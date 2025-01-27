/*
 * oxCore is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2014, Gluu
 */

package org.gluu.orm.couchbase.model;

import com.couchbase.client.java.query.dsl.Expression;

/**
 * Filter to Expression convertation result
 *
 * @author Yuriy Movchan Date: 06/21/2019
 */
public class ConvertedExpression {
	
	private Expression expression;
	private boolean consistency;

	private ConvertedExpression(Expression expression) {
		this.expression = expression;
	}

	private ConvertedExpression(Expression expression, boolean consistency) {
		this.expression = expression;
		this.consistency = consistency;
	}

	public static ConvertedExpression build(Expression expression, boolean consistency) {
		return new ConvertedExpression(expression, consistency);
	}

	public Expression expression() {
		return expression;
	}

	public boolean consistency() {
		return consistency;
	}

	public void consistency(boolean consistency) {
		this.consistency = consistency;
	}

	@Override
	public String toString() {
		return "ConvertedExpression [expression=" + expression + ", consistency=" + consistency + "]";
	}

}
