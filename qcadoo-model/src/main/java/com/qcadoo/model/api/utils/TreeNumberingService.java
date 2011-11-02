/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.9
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
package com.qcadoo.model.api.utils;

import java.util.Comparator;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityTree;
import com.qcadoo.model.api.EntityTreeNode;

/**
 * Helper service for automatically generating numbers for EntityTree nodes
 * 
 * @since 0.4.8
 */
public interface TreeNumberingService {

    /**
     * Generate new numbers for all nodes of the tree
     * 
     * @param tree
     *            tree to be numbered
     */
    public void generateTreeNumbers(final EntityTree tree);

    /**
     * Generate new numbers for all sub-nodes of given tree node
     * 
     * @param treeNode
     *            tree node to be numbered (with sub-nodes)
     */
    public void generateTreeNumbers(final EntityTreeNode treeNode);

    /**
     * Generate new numbers for all nodes of the tree
     * 
     * @param dd
     *            node component DataDefinition
     * @param joinFieldName
     *            name of tree field
     * @param belongsToEntityId
     *            id of owning tree entity
     */
    public void generateNumbersAndUpdateTree(final DataDefinition dd, final String joinFieldName, final Long belongsToEntityId);

    /**
     * Getter for tree node numbers comparator
     * 
     * @return instance of TreeNodesNumberComparator
     */
    public Comparator<Entity> getTreeNodesNumberComparator();

}