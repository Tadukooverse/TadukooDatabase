package com.github.tadukoo.database.mysql.syntax.conditional;

import com.github.tadukoo.database.mysql.syntax.SQLSyntaxUtil;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Conditional Statement represents a single conditional statement in MySQL (it may be part of a greater
 * {@link Conditional})
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class ConditionalStatement{
	
	/**
	 * A builder to create a {@link ConditionalStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>Condition Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>negated</td>
	 *         <td>Whether the statement is negated or not</td>
	 *         <td>Defaults to false</td>
	 *     </tr>
	 *     <tr>
	 *         <td>column</td>
	 *         <td>A {@link ColumnRef column reference} to the column of the statement</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>operator</td>
	 *         <td>The {@link SQLOperator operator} of the statement</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>value</td>
	 *         <td>The value of the statement</td>
	 *         <td>Required</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class ConditionStatementBuilder implements Column, Operator, Value, Build{
		/** Whether the statement is negated or not */
		private boolean negated = false;
		/** The {@link ColumnRef} to the column of the statement */
		private ColumnRef column = null;
		/** The {@link SQLOperator operator} of the statement */
		private SQLOperator operator = null;
		/** the value of the statement */
		private Object value = null;
		
		/** Not allowed to instantiate outside ConditionalStatement */
		private ConditionStatementBuilder(){ }
		
		/** {@inheritDoc} */
		public Column negated(boolean negated){
			this.negated = negated;
			return this;
		}
		
		/** {@inheritDoc} */
		public Column negated(){
			negated = true;
			return this;
		}
		
		/** {@inheritDoc} */
		public Operator column(ColumnRef column){
			this.column = column;
			return this;
		}
		
		/** {@inheritDoc} */
		public Value operator(SQLOperator operator){
			this.operator = operator;
			return this;
		}
		
		/** {@inheritDoc} */
		public Build value(Object value){
			this.value = value;
			return this;
		}
		
		/**
		 * Checks for any errors in the set parameters
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// column is required
			if(column == null){
				errors.add("column is required!");
			}
			
			// operator is required
			if(operator == null){
				errors.add("operator is required!");
			}
			
			// value is required
			if(value == null){
				errors.add("value is required!");
			}
			
			// report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered the following errors trying to build a " +
						"ConditionalStatement:\n" + StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		public ConditionalStatement build(){
			checkForErrors();
			
			return new ConditionalStatement(negated, column, operator, value);
		}
	}
	
	/** Whether this statement is negated or not */
	private final boolean negated;
	/** The column of this statement */
	private final ColumnRef column;
	/** The operator of this statement */
	private final SQLOperator operator;
	/** The value of this statement */
	private final Object value;
	
	/**
	 * Constructs a new conditional statement with the given parameters
	 *
	 * @param negated Whether the statement is negated or not
	 * @param column The column of the statement
	 * @param operator The operator of the statement
	 * @param value The value of the statement
	 */
	private ConditionalStatement(boolean negated, ColumnRef column, SQLOperator operator, Object value){
		this.negated = negated;
		this.column = column;
		this.operator = operator;
		this.value = value;
	}
	
	/**
	 * @return A new {@link ConditionStatementBuilder builder} to build a {@link ConditionalStatement}
	 */
	public static Column builder(){
		return new ConditionStatementBuilder();
	}
	
	/**
	 * @return Whether this statement is negated or not
	 */
	public boolean isNegated(){
		return negated;
	}
	
	/**
	 * @return The column of this statement
	 */
	public ColumnRef getColumn(){
		return column;
	}
	
	/**
	 * @return The operator of this statement
	 */
	public SQLOperator getOperator(){
		return operator;
	}
	
	/**
	 * @return The value of this statement
	 */
	public Object getValue(){
		return value;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return (negated?"NOT ":"") + column.toString() + " " + operator.toString() + " " +
				SQLSyntaxUtil.convertValueToString(value);
	}
	
	/*
	 * Interfaces for Builder
	 */
	
	/**
	 * The {@link ColumnRef Column} part of building a {@link ConditionalStatement}
	 */
	public interface Column{
		/**
		 * @param negated Whether the statement is negated or not
		 * @return this, to continue building
		 */
		Column negated(boolean negated);
		
		/**
		 * Sets negated to true for the statement
		 *
		 * @return this, to continue building
		 */
		Column negated();
		
		/**
		 * @param column The {@link ColumnRef} to the column of the statement
		 * @return this, to continue building
		 */
		Operator column(ColumnRef column);
	}
	
	/**
	 * The {@link SQLOperator Operator} part of building a {@link ConditionalStatement}
	 */
	public interface Operator{
		/**
		 * @param operator The {@link SQLOperator operator} of the statement
		 * @return this, to continue building
		 */
		Value operator(SQLOperator operator);
	}
	
	/**
	 * The Value part of building a {@link ConditionalStatement}
	 */
	public interface Value{
		/**
		 * @param value The value of the statement
		 * @return this, to continue building
		 */
		Build value(Object value);
	}
	
	/**
	 * The building part of building a {@link ConditionalStatement}
	 */
	public interface Build{
		/**
		 * Builds a new {@link ConditionalStatement} using the set parameters
		 *
		 * @return The newly built {@link ConditionalStatement}
		 */
		ConditionalStatement build();
	}
}
