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
package com.qcadoo.model.internal.resourceresolver;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.springframework.core.io.Resource;

import com.qcadoo.model.internal.api.ModelXmlResolver;
import com.qcadoo.model.internal.module.ModelXmlHolder;
import com.qcadoo.model.internal.resolver.ModelXmlResolverImpl;

public class ModelXmlResourceResolverTest {

    @Test
    public void shouldReturnAllMatchingResources() throws Exception {
        // given
        ModelXmlResolver resourceResolver = new ModelXmlResolverImpl();
        ((ModelXmlHolder) resourceResolver).put("full", "firstEntity", new FileInputStream(new File(
                "src/test/resources/model/full/firstEntity.xml")));

        // when
        Resource[] resources = resourceResolver.getResources();

        // then
        assertEquals(1, resources.length);
    }

}
