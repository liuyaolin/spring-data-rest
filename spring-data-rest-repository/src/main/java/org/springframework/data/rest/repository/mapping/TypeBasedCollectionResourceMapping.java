/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.rest.repository.mapping;

import java.lang.reflect.Modifier;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.rest.core.Path;
import org.springframework.data.rest.repository.annotation.RestResource;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.core.EvoInflectorRelProvider;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * {@link CollectionResourceMapping} based on a type. Will derive default relation types and pathes from the type but
 * inspect it for {@link RestResource} annotations for customization.
 * 
 * @author Oliver Gierke
 */
class TypeBasedCollectionResourceMapping implements CollectionResourceMapping {

	private final Class<?> type;
	private final RestResource annotation;
	private final RelProvider relProvider;

	/**
	 * Creates a new {@link TypeBasedCollectionResourceMapping} using the given type.
	 * 
	 * @param type must not be {@literal null}.
	 */
	public TypeBasedCollectionResourceMapping(Class<?> type) {
		this(type, new EvoInflectorRelProvider());
	}

	/**
	 * Creates a new {@link TypeBasedCollectionResourceMapping} using the given type and {@link RelProvider}.
	 * 
	 * @param type must not be {@literal null}.
	 * @param relProvider must not be {@literal null}.
	 */
	public TypeBasedCollectionResourceMapping(Class<?> type, RelProvider relProvider) {

		Assert.notNull(type, "Type must not be null!");
		Assert.notNull(relProvider, "RelProvider must not be null!");

		this.type = type;
		this.relProvider = relProvider;
		this.annotation = AnnotationUtils.findAnnotation(type, RestResource.class);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.rest.repository.mapping.ResourceMapping#getPath()
	 */
	@Override
	public Path getPath() {

		String path = annotation == null ? null : annotation.path().trim();
		path = StringUtils.hasText(path) ? path : StringUtils.uncapitalize(type.getSimpleName());
		return new Path(path);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.rest.repository.mapping.ResourceMapping#isExported()
	 */
	@Override
	public Boolean isExported() {
		return annotation == null ? Modifier.isPublic(type.getModifiers()) : annotation.exported();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.rest.repository.mapping.ResourceMapping#getRel()
	 */
	@Override
	public String getRel() {

		if (annotation == null || !StringUtils.hasText(annotation.rel())) {
			return relProvider.getCollectionResourceRelFor(type);
		}

		return annotation.rel();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.rest.repository.mapping.CollectionResourceMapping#getSingleResourceRel()
	 */
	@Override
	public String getSingleResourceRel() {
		return relProvider.getSingleResourceRelFor(type);
	}
}
