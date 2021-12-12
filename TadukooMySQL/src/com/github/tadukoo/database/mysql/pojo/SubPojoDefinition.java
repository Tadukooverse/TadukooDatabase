package com.github.tadukoo.database.mysql.pojo;

import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Sub Pojo Definition holds all the information for a sub pojo contained inside another pojo
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SubPojoDefinition{
	
	/**
	 * A builder used to build a {@link SubPojoDefinition}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>Sub Pojo Definition Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>key</td>
	 *         <td>The key the sub pojo is stored using in the pojo</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>idCol</td>
	 *         <td>The ID column for the sub pojo's ID stored in the pojo</td>
	 *         <td>Required if type or junction is specified, otherwise defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>type</td>
	 *         <td>The class used for the sub pojo, in case the pojo has to create a new instance of it</td>
	 *         <td>Required if junction is specified, otherwise defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>junction</td>
	 *         <td>The junction to use for this sub pojo when doing a search on the pojo</td>
	 *         <td>Required if type is specified, otherwise defaults to null</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SubPojoDefinitionBuilder implements Key, IDColumn, TypeAndJunction, Build{
		/** The key the sub pojo is stored using in the pojo */
		private String key;
		/** The ID column for the sub pojo's ID stored in the pojo */
		private String idCol = null;
		/** The class used for the sub pojo, in case the pojo has to create a new instance of it */
		private Class<? extends DatabasePojo> type = null;
		/** The junction to use for this sub pojo when doing a search on the pojo */
		private Pair<ColumnRef, ColumnRef> junction = null;
		
		/** Not allowed to instantiate outside {@link SubPojoDefinition} */
		private SubPojoDefinitionBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		public IDColumn key(String key){
			this.key = key;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public TypeAndJunction idCol(String idCol){
			this.idCol = idCol;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build typeAndJunction(Class<? extends DatabasePojo> type, Pair<ColumnRef, ColumnRef> junction){
			this.type = type;
			this.junction = junction;
			return this;
		}
		
		/**
		 * Checks for any errors in the set parameters and throws an error if any problems are found
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// key is required
			if(StringUtil.isBlank(key)){
				errors.add("key is required!");
			}
			
			// If type or junction is specified, both them and idCol are required
			if((type != null || junction != null) && (type == null || junction == null || StringUtil.isBlank(idCol))){
				errors.add("Type or junction is specified - in this case, type, junction, and idCol are required!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Errors encountered trying to build a SubPojoDefinition: \n" +
						StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		@Override
		public SubPojoDefinition build(){
			checkForErrors();
			
			return new SubPojoDefinition(key, idCol, type, junction);
		}
	}
	
	/** The key the sub pojo is stored using in the pojo */
	private final String key;
	/** The ID column for the sub pojo's ID stored in the pojo */
	private final String idCol;
	/** The class used for the sub pojo, in case the pojo has to create a new instance of it */
	private final Class<? extends DatabasePojo> type;
	/** The junction to use for this sub pojo when doing a search on the pojo */
	private final Pair<ColumnRef, ColumnRef> junction;
	
	/**
	 * Constructs a new {@link SubPojoDefinition} using the given parameters
	 *
	 * @param key The key the sub pojo is stored using in the pojo
	 * @param idCol The ID column for the sub pojo's ID stored in the pojo
	 * @param type The class used for the sub pojo, in case the pojo has to create a new instance of it
	 * @param junction The junction to use for this sub pojo when doing a search on the pojo
	 */
	private SubPojoDefinition(
			String key, String idCol, Class<? extends DatabasePojo> type, Pair<ColumnRef, ColumnRef> junction){
		this.key = key;
		this.idCol = idCol;
		this.type = type;
		this.junction = junction;
	}
	
	/**
	 * @return A {@link SubPojoDefinition builder} to use to create a new {@link SubPojoDefinition}
	 */
	public static Key builder(){
		return new SubPojoDefinitionBuilder();
	}
	
	/**
	 * @return The key the sub pojo is stored using in the pojo
	 */
	public String getKey(){
		return key;
	}
	
	/**
	 * @return The ID column for the sub pojo's ID stored in the pojo
	 */
	public String getIDCol(){
		return idCol;
	}
	
	/**
	 * @return The class used for the sub pojo, in case the pojo has to create a new instance of it
	 */
	public Class<? extends DatabasePojo> getType(){
		return type;
	}
	
	/**
	 * @return The junction to use for this sub pojo when doing a search on the pojo
	 */
	public Pair<ColumnRef, ColumnRef> getJunction(){
		return junction;
	}
	
	/**
	 * The Key part of building a {@link SubPojoDefinition}
	 */
	public interface Key{
		/**
		 * @param key The key the sub pojo is stored using in the pojo
		 * @return this, to continue building
		 */
		IDColumn key(String key);
	}
	
	/**
	 * The ID column part of building a {@link SubPojoDefinition}
	 */
	public interface IDColumn extends Build{
		/**
		 * @param idCol The ID column for the sub pojo's ID stored in the pojo
		 * @return this, to continue building
		 */
		TypeAndJunction idCol(String idCol);
	}
	
	/**
	 * The Type and Junction part of building a {@link SubPojoDefinition}
	 */
	public interface TypeAndJunction extends Build{
		/**
		 * @param type The class used for the sub pojo, in case the pojo has to create a new instance of it
		 * @param junction The junction to use for this sub pojo when doing a search on the pojo
		 * @return this, to continue building
		 */
		Build typeAndJunction(Class<? extends DatabasePojo> type, Pair<ColumnRef, ColumnRef> junction);
	}
	
	/**
	 * The building part of building a {@link SubPojoDefinition}
	 */
	public interface Build{
		/**
		 * Builds a new {@link SubPojoDefinition} using the set parameters after checking for problems
		 *
		 * @return The newly built {@link SubPojoDefinition}
		 */
		SubPojoDefinition build();
	}
}
