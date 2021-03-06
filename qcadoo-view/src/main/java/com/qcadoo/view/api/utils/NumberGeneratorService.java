/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.view.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchOrders;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.search.SearchResult;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FieldComponent;
import com.qcadoo.view.api.components.FormComponent;

/**
 * Helper service for automatically generating numbers for entities
 * 
 * @since 0.4.0
 */
@Service
public class NumberGeneratorService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    /**
     * Generates and inserts new number to entity's form
     * 
     * @param state
     *            main view state definition
     * @param plugin
     *            plugin identifier of entity
     * @param entityName
     *            name of entity
     * @param formFieldReferenceName
     *            reference name of form
     * @param numberFieldReferenceName
     *            reference name of field into which generated number should be inserted
     */
    public void generateAndInsertNumber(final ViewDefinitionState state, final String plugin, final String entityName,
            final String formFieldReferenceName, final String numberFieldReferenceName) {
        if (!checkIfShouldInsertNumber(state, formFieldReferenceName, numberFieldReferenceName)) {
            return;
        }
        FieldComponent number = (FieldComponent) state.getComponentByReference(numberFieldReferenceName);
        number.setFieldValue(generateNumber(plugin, entityName));
    }

    /**
     * Checks if new entity number should be generated and inserted
     * 
     * @param state
     *            main view state definition
     * @param formFieldReferenceName
     *            reference name of form
     * @param numberFieldReferenceName
     *            reference name of field into which generated number should be inserted
     * @return true if new entity number should be generated and inserted
     */
    public boolean checkIfShouldInsertNumber(final ViewDefinitionState state, final String formFieldReferenceName,
            final String numberFieldReferenceName) {
        FormComponent form = (FormComponent) state.getComponentByReference(formFieldReferenceName);
        FieldComponent number = (FieldComponent) state.getComponentByReference(numberFieldReferenceName);
        if (form.getEntityId() != null) {
            // form is already saved
            return false;
        }
        if (StringUtils.hasText((String) number.getFieldValue())) {
            // number is already choosen
            return false;
        }
        if (number.isHasError()) {
            // there is a validation message for that field
            return false;
        }
        return true;
    }

    /**
     * Generate new 6-digits number of entity
     * 
     * @param plugin
     *            plugin identifier of entity
     * @param entityName
     *            name of entity
     * @return new number of entity
     */
    public String generateNumber(final String plugin, final String entityName) {
        return generateNumber(plugin, entityName, 6);
    }

    /**
     * Generate new number of entity with specified digits number
     * 
     * @param plugin
     *            plugin identifier of entity
     * @param entityName
     *            name of entity
     * @param digitsNumber
     *            number of digits of generated number
     * @return new number of entity
     */
    public String generateNumber(final String plugin, final String entityName, final int digitsNumber) {
        DataDefinition dataDefinition = dataDefinitionService.get(plugin, entityName);

        // TODO MAKU add projection
        SearchResult results = dataDefinition.find().addOrder(SearchOrders.desc("number")).list();

        long longValue = 1;
        for (Entity entity : results.getEntities()) {
            String number = entity.getStringField("number");
            if (!org.apache.commons.lang.StringUtils.isNumeric(number)) {
                continue;
            }
            longValue = Long.valueOf(number);
            if (numberAlreadyExist(dataDefinition, longValue, digitsNumber)) {
                longValue++;
                break;
            }
        }

        return String.format("%0" + digitsNumber + "d", longValue);
    }

    private boolean numberAlreadyExist(final DataDefinition dataDefinition, final long longValue, final int digitsNumber) {
        // TODO MAKU add projection
        return dataDefinition.find().add(SearchRestrictions.eq("number", String.format("%0" + digitsNumber + "d", longValue)))
                .setMaxResults(1).uniqueResult() != null;
    }
}
