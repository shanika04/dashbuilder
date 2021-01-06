// $Id$
/*
* JBoss, Home of Professional Open Source
* Copyright 2010, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.hibernate.validator.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraints.ScriptAssert;
import org.hibernate.validator.constraints.impl.scriptassert.ScriptEvaluator;
import org.hibernate.validator.constraints.impl.scriptassert.ScriptEvaluatorFactory;

/**
 * Validator for the {@link ScriptAssert} constraint annotation.
 *
 * @author Gunnar Morling.
 */
public class ScriptAssertValidator implements ConstraintValidator<ScriptAssert, Object> {

	private String script;

	private String languageName;

	private String alias;

	public void initialize(ScriptAssert constraintAnnotation) {

		validateParameters( constraintAnnotation );

		this.script = constraintAnnotation.script();
		this.languageName = constraintAnnotation.lang();
		this.alias = constraintAnnotation.alias();
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

		ScriptEvaluator scriptEvaluator = ScriptEvaluatorFactory.getInstance()
				.getScriptEvaluatorByLanguageName( languageName );

		return scriptEvaluator.evaluate( script, value, alias );
	}

	private void validateParameters(ScriptAssert constraintAnnotation) {

		if ( constraintAnnotation.script().isEmpty() ) {
			throw new IllegalArgumentException( "The parameter \"script\" must not be empty." );
		}
		if ( constraintAnnotation.lang().isEmpty() ) {
			throw new IllegalArgumentException( "The parameter \"lang\" must not be empty." );
		}
		if ( constraintAnnotation.alias().isEmpty() ) {
			throw new IllegalArgumentException( "The parameter \"alias\" must not be empty." );
		}
	}
}
