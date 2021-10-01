package com.github.tadukoo.database.mysql.syntax.conditional;

import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Conditional represents a (potentially complex) conditional in MySQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class Conditional{
	
	/**
	 * Conditional Builder is used to build a {@link Conditional}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>Conditional Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>firstCond</td>
	 *         <td>The first {@link Conditional} involved (it may be null if a {@link ConditionalStatement}
	 *         is used instead)</td>
	 *         <td>Defaults to null (must specify this or firstCondStmt)</td>
	 *     </tr>
	 *     <tr>
	 *         <td>firstCondStmt</td>
	 *         <td>The first {@link ConditionalStatement} involved (it may be null if a {@link Conditional}
	 *         is used instead)</td>
	 *         <td>Defaults to null (must specify this or firstCond)</td>
	 *     </tr>
	 *     <tr>
	 *         <td>operator</td>
	 *         <td>The {@link SQLConjunctiveOperator operator} for the conditional (it may be null if we have
	 *         just one condition)</td>
	 *         <td>Defaults to null (required if you specify secondCond or secondCondStmt)</td>
	 *     </tr>
	 *     <tr>
	 *         <td>secondCond</td>
	 *         <td>The second {@link Conditional} involved (it may be null if either a {@link ConditionalStatement}
	 *         is used instead or if there's only one condition)</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>secondCondStmt</td>
	 *         <td>The second {@link ConditionalStatement} involved (it may be null if either a {@link Conditional}
	 *         is used instead or if there's only one condition)</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class ConditionalBuilder{
		/** The first {@link Conditional} involved (it may be null if a {@link ConditionalStatement} is used instead) */
		private Conditional firstCond;
		/** The first {@link ConditionalStatement} involved (it may be null if a {@link Conditional} is used instead) */
		private ConditionalStatement firstCondStmt;
		/** The {@link SQLConjunctiveOperator operator} for the conditional (it may be null if we have just one condition) */
		private SQLConjunctiveOperator operator;
		/** The second {@link Conditional} involved (it may be null if either a {@link ConditionalStatement} is used
		 * instead or if there's only one condition) */
		private Conditional secondCond;
		/** The second {@link ConditionalStatement} involved (it may be null if either a {@link Conditional} is used
		 * instead or if there's only one condition) */
		private ConditionalStatement secondCondStmt;
		
		/** Not allowed to instantiate outside Conditional */
		private ConditionalBuilder(){ }
		
		/**
		 * @param firstCond The first {@link Conditional} involved (it may be null if a {@link ConditionalStatement}
		 *                  is used instead)
		 * @return this, to continue building
		 */
		public ConditionalBuilder firstCond(Conditional firstCond){
			this.firstCond = firstCond;
			return this;
		}
		
		/**
		 * @param firstCondStmt The first {@link ConditionalStatement} involved (it may be null if a {@link Conditional}
		 *                      is used instead)
		 * @return this, to continue building
		 */
		public ConditionalBuilder firstCondStmt(ConditionalStatement firstCondStmt){
			this.firstCondStmt = firstCondStmt;
			return this;
		}
		
		/**
		 * @param operator The {@link SQLConjunctiveOperator operator} for the conditional (it may be null if we have
		 *                 just one condition)
		 * @return this, to continue building
		 */
		public ConditionalBuilder operator(SQLConjunctiveOperator operator){
			this.operator = operator;
			return this;
		}
		
		/**
		 * @param secondCond The second {@link Conditional} involved (it may be null if either a
		 *                   {@link ConditionalStatement} is used instead or if there's only one condition)
		 * @return this, to continue building
		 */
		public ConditionalBuilder secondCond(Conditional secondCond){
			this.secondCond = secondCond;
			return this;
		}
		
		/**
		 * @param secondCondStmt The second {@link ConditionalStatement} involved (it may be null if either a
		 *                       {@link Conditional} is used instead or if there's only one condition)
		 * @return this, to continue building
		 */
		public ConditionalBuilder secondCondStmt(ConditionalStatement secondCondStmt){
			this.secondCondStmt = secondCondStmt;
			return this;
		}
		
		/**
		 * Checks for any errors in the set parameters
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// Must specify either firstCond or firstCondStmt
			if(firstCond == null && firstCondStmt == null){
				errors.add("Must specify either firstCond or firstCondStmt!");
			}
			
			// Can't specify both firstCond and firstCondStmt
			if(firstCond != null && firstCondStmt != null){
				errors.add("Can't specify both firstCond and firstCondStmt!");
			}
			
			// Can't specify both secondCond and secondCondStmt
			if(secondCond != null && secondCondStmt != null){
				errors.add("Can't specify both secondCond and secondCondStmt!");
			}
			
			// Can't specify a single conditional by itself
			if(firstCond != null && secondCond == null && secondCondStmt == null){
				errors.add("Can't specify a single conditional - that's already a conditional");
			}
			
			// Must specify operator if it's two conditions
			if((secondCond != null || secondCondStmt != null) && operator == null){
				errors.add("Must specify an operator if you have two conditions!");
			}
			
			// Can't specify operator if there's only one condition
			if(secondCond == null && secondCondStmt == null && operator != null){
				errors.add("Can't specify operator with only one condition!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered the following errors trying to build a " +
						"Conditional:\n" + StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Constructs a new {@link Conditional} with the set parameters after checking for errors
		 *
		 * @return The newly built {@link Conditional}
		 */
		public Conditional build(){
			checkForErrors();
			
			return new Conditional(firstCond, firstCondStmt, operator, secondCond, secondCondStmt);
		}
	}
	
	/** The first {@link Conditional} involved (it may be null if a {@link ConditionalStatement} is used instead) */
	private final Conditional firstCond;
	/** The first {@link ConditionalStatement} involved (it may be null if a {@link Conditional} is used instead) */
	private final ConditionalStatement firstCondStmt;
	/** The {@link SQLConjunctiveOperator operator} for the conditional (it may be null if we have just one condition) */
	private final SQLConjunctiveOperator operator;
	/** The second {@link Conditional} involved (it may be null if either a {@link ConditionalStatement} is used
	 * instead or if there's only one condition) */
	private final Conditional secondCond;
	/** The second {@link ConditionalStatement} involved (it may be null if either a {@link Conditional} is used
	 * instead or if there's only one condition) */
	private final ConditionalStatement secondCondStmt;
	
	/**
	 * Constructs a new Conditional using the given parameters
	 *
	 * @param firstCond The first {@link Conditional} involved
	 * @param firstCondStmt The first {@link ConditionalStatement} involved
	 * @param operator The {@link SQLConjunctiveOperator operator} for the conditional
	 * @param secondCond The second {@link Conditional} involved
	 * @param secondCondStmt The second {@link ConditionalStatement} involved
	 */
	private Conditional(
			Conditional firstCond, ConditionalStatement firstCondStmt, SQLConjunctiveOperator operator,
			Conditional secondCond, ConditionalStatement secondCondStmt){
		this.firstCond = firstCond;
		this.firstCondStmt = firstCondStmt;
		this.operator = operator;
		this.secondCond = secondCond;
		this.secondCondStmt = secondCondStmt;
	}
	
	/**
	 * @return A new {@link ConditionalBuilder builder} to use to build a {@link Conditional}
	 */
	public static ConditionalBuilder builder(){
		return new ConditionalBuilder();
	}
	
	/**
	 * @return The first {@link Conditional} involved (it may be null if a {@link ConditionalStatement}
	 * is used instead)
	 */
	public Conditional getFirstCond(){
		return firstCond;
	}
	
	/**
	 * @return The first {@link ConditionalStatement} involved (it may be null if a {@link Conditional}
	 * is used instead)
	 */
	public ConditionalStatement getFirstCondStmt(){
		return firstCondStmt;
	}
	
	/**
	 * @return The {@link SQLConjunctiveOperator operator} for the conditional (it may be null if
	 * we have just one condition)
	 */
	public SQLConjunctiveOperator getOperator(){
		return operator;
	}
	
	/**
	 * @return The second {@link Conditional} involved (it may be null if either a {@link ConditionalStatement}
	 * is used instead or if there's only one condition)
	 */
	public Conditional getSecondCond(){
		return secondCond;
	}
	
	/**
	 * @return The second {@link ConditionalStatement} involved (it may be null if either a {@link Conditional}
	 * is used instead or if there's only one condition)
	 */
	public ConditionalStatement getSecondCondStmt(){
		return secondCondStmt;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		StringBuilder conditional = new StringBuilder();
		
		// Determine if we have a second condition and if first and second are conditionals or conditional statements
		boolean haveSecondCond = secondCond != null || secondCondStmt != null;
		boolean firstIsCond = firstCond != null;
		boolean secondIsCond = secondCond != null;
		
		// If we have the second condition and the first is a conditional, it needs parentheses
		if(haveSecondCond && firstIsCond){
			conditional.append('(');
		}
		// Add first condition
		conditional.append(firstIsCond?firstCond.toString():firstCondStmt.toString());
		if(haveSecondCond && firstIsCond){
			conditional.append(')');
		}
		
		// Add second condition if we have it
		if(haveSecondCond){
			// Add the operator
			conditional.append(" ").append(operator.toString()).append(" ");
			
			// If the second condition is a conditional, it needs parentheses
			if(secondIsCond){
				conditional.append('(').append(secondCond).append(')');
			}else{
				conditional.append(secondCondStmt);
			}
		}
		
		// Return the string we built
		return conditional.toString();
	}
}
