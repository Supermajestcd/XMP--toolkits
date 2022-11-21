/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.causeway.viewer.wicket.ui.components.collectioncontents.multiple;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;

import org.apache.causeway.core.metamodel.interactions.managed.nonscalar.DataTableModel;
import org.apache.causeway.viewer.commons.model.components.UiComponentType;
import org.apache.causeway.viewer.wicket.model.hints.CausewayEnvelopeEvent;
import org.apache.causeway.viewer.wicket.model.hints.CausewaySelectorEvent;
import org.apache.causeway.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.causeway.viewer.wicket.model.models.EntityCollectionModelAbstract;
import org.apache.causeway.viewer.wicket.model.models.EntityCollectionModelHidden;
import org.apache.causeway.viewer.wicket.model.models.EntityCollectionModelParented;
import org.apache.causeway.viewer.wicket.model.util.ComponentHintKey;
import org.apache.causeway.viewer.wicket.ui.ComponentFactory;
import org.apache.causeway.viewer.wicket.ui.components.collection.count.CollectionCountProvider;
import org.apache.causeway.viewer.wicket.ui.components.collection.selector.CollectionPresentationSelectorHelper;
import org.apache.causeway.viewer.wicket.ui.components.collection.selector.CollectionPresentationSelectorPanel;
import org.apache.causeway.viewer.wicket.ui.components.collection.selector.CollectionPresentationSelectorProvider;
import org.apache.causeway.viewer.wicket.ui.panels.PanelAbstract;

/**
 * Subscribes to events generated by
 * {@link org.apache.causeway.viewer.wicket.ui.components.collection.selector.CollectionPresentationSelectorPanel},
 * rendering the appropriate {@link UiComponentType#COLLECTION_CONTENTS}
 * view for a backing {@link EntityCollectionModel}.
 */
public class CollectionContentsMultipleViewsPanel
extends PanelAbstract<DataTableModel, EntityCollectionModel>
implements CollectionCountProvider {

    private static final long serialVersionUID = 1L;

    private static final int MAX_NUM_UNDERLYING_VIEWS = 10;

    private static final String UIHINT_VIEW = EntityCollectionModelParented.HINT_KEY_SELECTED_ITEM;

    private final String underlyingIdPrefix;
    private final CollectionPresentationSelectorHelper selectorHelper;

    private Component selectedComponent;

    private Component[] underlyingViews;

    public CollectionContentsMultipleViewsPanel(
            final String id,
            final EntityCollectionModel model) {
        super(id, model);

        this.underlyingIdPrefix = UiComponentType.COLLECTION_CONTENTS.toString();

        final ComponentHintKey selectedItemSessionAttribute =
                model.getVariant().isParented()
                    ? ComponentHintKey.create(super.getMetaModelContext(), this, UIHINT_VIEW)
                    : null;

        selectorHelper = new CollectionPresentationSelectorHelper(
                model, getComponentFactoryRegistry(), selectedItemSessionAttribute);
    }

    /**
     * Build UI only after added to parent.
     */
    @Override
    public void onInitialize() {
        super.onInitialize();
        addUnderlyingViews();
    }

    private void addUnderlyingViews() {
        final EntityCollectionModel visibleCollModel = getModel();
        final EntityCollectionModel hiddenCollModel = EntityCollectionModelHidden
                .forCollectionModel((EntityCollectionModelAbstract) visibleCollModel);

        final List<ComponentFactory> componentFactories = selectorHelper.getComponentFactories();

        final CollectionPresentationSelectorPanel selectorDropdownPanelIfAny =
                CollectionPresentationSelectorProvider.getCollectionSelectorProvider(this);
        final String selectedCompFactoryName = selectorDropdownPanelIfAny != null
            ? selectorHelper.honourViewHintElseDefault(selectorDropdownPanelIfAny)
            : componentFactories.get(0).getName();


        // create all, hide the one not selected
        int i = 0;
        int selectedIdx = 0;
        underlyingViews = new Component[MAX_NUM_UNDERLYING_VIEWS];

        for (ComponentFactory componentFactory : componentFactories) {
            final String underlyingId = underlyingIdPrefix + "-" + i;

            final boolean isSelected = selectedCompFactoryName.equals(componentFactory.getName());
            final Component underlyingView = componentFactory
                    .createComponent(underlyingId, isSelected ? visibleCollModel : hiddenCollModel);
            if(isSelected) {
                selectedIdx = i;
            }
            underlyingViews[i++] = underlyingView;
            this.addOrReplace(underlyingView);
        }

        // hide any unused placeholders
        while(i<MAX_NUM_UNDERLYING_VIEWS) {
            String underlyingId = underlyingIdPrefix + "-" + i;
            permanentlyHide(underlyingId);
            i++;
        }

        this.setOutputMarkupId(true);

        for(i=0; i<MAX_NUM_UNDERLYING_VIEWS; i++) {
            Component component = underlyingViews[i];
            if(component != null) {
                if(i != selectedIdx) {
                    super.setVisible(component, /*visible*/ false);
                } else {
                    selectedComponent = component;
                }
            }
        }
    }

    @Override
    public void onEvent(final IEvent<?> event) {
        super.onEvent(event);

        final CausewaySelectorEvent selectorEvent = CausewayEnvelopeEvent.openLetter(event, CausewaySelectorEvent.class);
        if(selectorEvent == null) {
            return;
        }
        final CollectionPresentationSelectorPanel selectorDropdownPanel = CollectionPresentationSelectorProvider.getCollectionSelectorProvider(this);
        if(selectorDropdownPanel == null) {
            // not expected, because this event shouldn't be called.
            // but no harm in simply returning...
            return;
        }

        String selectedView = selectorEvent.hintFor(selectorDropdownPanel, UIHINT_VIEW);
        if (selectedView == null) {
            return;
        }

        int underlyingViewNum = selectorHelper.lookup(selectedView);

        final EntityCollectionModel dummyModel = EntityCollectionModelHidden
                .forCollectionModel((EntityCollectionModelAbstract) getModel());
        for(int i=0; i<MAX_NUM_UNDERLYING_VIEWS; i++) {
            final Component component = underlyingViews[i];
            if(component == null) {
                continue;
            }
            final boolean isSelected = i == underlyingViewNum;
            setVisible(component, isSelected);
            component.setDefaultModel(isSelected? getModel(): dummyModel);
        }

        this.selectedComponent = underlyingViews[underlyingViewNum];

        final AjaxRequestTarget target = selectorEvent.getTarget();
        if(target != null) {
            target.add(this, selectorDropdownPanel);
        }
    }

    @Override
    public Integer getCount() {
        if(selectedComponent instanceof CollectionCountProvider) {
            final CollectionCountProvider collectionCountProvider = (CollectionCountProvider) selectedComponent;
            return collectionCountProvider.getCount();
        } else {
            return null;
        }
    }


}
