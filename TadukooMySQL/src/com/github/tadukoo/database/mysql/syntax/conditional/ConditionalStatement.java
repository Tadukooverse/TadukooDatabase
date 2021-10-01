package com.github.tadukoo.database.mysql.syntax.conditional;

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
	public static class ConditionStatementBuilder{
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
		
		/**
		 * @param negated Whether the statement is negated or not
		 * @return this, to continue building
		 */
		public ConditionStatementBuilder negated(boolean negated){
			this.negated = negated;
			return this;
		}
		
		/**
		 * Sets negated to true for the statement
		 *
		 * @return this, to continue building
		 */
		public ConditionStatementBuilder negated(){
			negated = true;
			return this;
		}
		
		/**
		 * @param column The {@link ColumnRef} to the column of the statement
		 * @return this, to continue building
		 */
		public ConditionStatementBuilder column(ColumnRef column){
			this.column = column;
			return this;
		}
		
		/**
		 * @param operator The {@link SQLOperator operator} of the statement
		 * @return this, to continue building
		 */
		public ConditionStatementBuilder operator(SQLOperator operator){
			this.operator = operator;
			return this;
		}
		
		/**
		 * @param value The value of the statement
		 * @return this, to continue building
		 */
		public ConditionStatementBuilder value(Object value){
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
		
		/**
		 * Builds a new {@link ConditionalStatement} using the set parameters
		 *
		 * @return The newly built {@link ConditionalStatement}
		 */
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
	public static ConditionStatementBuilder builder(){
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
	
	/**
	 * Converts the given object into a string to use for a value in MySQL
	 *
	 * @param value The object to convert
	 * @return The string representing the given value
	 */
	private String convertValueToString(Object value){
		if(value instanceof String s){
			return "'" + s + "'";
		}else if(value instanceof Integer i){
			return i.toString();
		}else if(value instanceof Boolean b){
			return b.toString();
		}else{
			return value.toString();
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return (negated?"NOT ":"") + column.toString() + " " + operator.toString() + " " + convertValueToString(value);
	}
}
