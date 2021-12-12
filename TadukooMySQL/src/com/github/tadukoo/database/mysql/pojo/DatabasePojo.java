package com.github.tadukoo.database.mysql.pojo;

import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.ForeignKeyConstraint;
import com.github.tadukoo.database.mysql.syntax.SQLSyntaxUtil;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.statement.SQLCreateStatement;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.pojo.MappedPojo;
import com.github.tadukoo.util.tuple.Pair;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Database Pojo represents a {@link MappedPojo} that can be used with a {@link Database}, to store and retrieve
 * values from the {@link Database} as needed.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public interface DatabasePojo extends MappedPojo{
	
	/**
	 * @return The name of the table for this pojo
	 */
	String getTableName();
	
	/**
	 * @return The name of the ID column for this pojo
	 */
	String getIDColumnName();
	
	/**
	 * @return A Map of all the {@link ColumnDefinition column definitions} in this pojo
	 */
	Map<String, ColumnDefinition> getColumnDefs();
	
	/**
	 * @return A Map of all {@link SubPojoDefinition subPojo definitions} in this pojo
	 */
	Map<String, SubPojoDefinition> getSubPojoDefs();
	
	/**
	 * @return The keys for any pojos stored inside this pojo
	 */
	default Set<String> getSubPojoKeys(){
		return getSubPojoDefs().keySet();
	}
	
	/**
	 * @param subPojoKey The subPojoKey to use to find a subPojoDefinition
	 * @return The {@link SubPojoDefinition} for the given subPojoKey
	 */
	default SubPojoDefinition getSubPojoDefBySubPojoKey(String subPojoKey){
		return getSubPojoDefs().get(subPojoKey);
	}
	
	/**
	 * @return The List of {@link ForeignKeyConstraint foreign keys} for this pojo
	 */
	List<ForeignKeyConstraint> getForeignKeys();
	
	/**
	 * Adds the given {@link ForeignKeyConstraint foreign key} to this pojo
	 *
	 * @param foreignKey The {@link ForeignKeyConstraint foreign key} to be added to the pojo
	 */
	default void addForeignKey(ForeignKeyConstraint foreignKey){
		getForeignKeys().add(foreignKey);
	}
	
	/**
	 * Adds the info for a subPojo using the given parameters
	 *
	 * @param subPojoDef The {@link SubPojoDefinition} to be added to this pojo
	 * @param subPojo The subPojo itself to be stored (may be null)
	 * @param foreignKey The {@link ForeignKeyConstraint foreign key} for the subPojo (to use when setting up the table)
	 */
	default void addSubPojo(SubPojoDefinition subPojoDef, DatabasePojo subPojo, ForeignKeyConstraint foreignKey){
		setItem(subPojoDef.getKey(), subPojo);
		getSubPojoDefs().put(subPojoDef.getKey(), subPojoDef);
		if(foreignKey != null){
			getForeignKeys().add(foreignKey);
		}
	}
	
	/**
	 * Adds the given {@link ColumnDefinition} to the Map of {@link ColumnDefinition column definitions}
	 *
	 * @param columnDef The {@link ColumnDefinition} to add to the Map
	 */
	default void addColumnDef(ColumnDefinition columnDef){
		addColumnDef(columnDef, null);
	}
	
	/**
	 * Adds the given {@link ColumnDefinition} to the Map of {@link ColumnDefinition column definitions} and
	 * sets the column value to the given value
	 *
	 * @param columnDef The {@link ColumnDefinition} to add to the Map
	 * @param value The value to set for the column
	 */
	default void addColumnDef(ColumnDefinition columnDef, Object value){
		String key = columnDef.getColumnName();
		setItem(key, value);
		getColumnDefs().put(key, columnDef);
	}
	
	/**
	 * Should be called in the constructor of a {@link DatabasePojo} and be used to create the
	 * {@link ColumnDefinition column definitions}
	 */
	void setDefaultColumnDefs();
	
	/**
	 * @return The Set of {@link ColumnDefinition column definition} keys
	 */
	default Set<String> getColumnDefKeys(){
		return getColumnDefs().keySet();
	}
	
	/**
	 * Retrieve a {@link ColumnDefinition} from the Map using the given key
	 *
	 * @param key The key for the {@link ColumnDefinition}
	 * @return The {@link ColumnDefinition} from the Map
	 */
	default ColumnDefinition getColumnDefByKey(String key){
		return getColumnDefs().get(key);
	}
	
	/**
	 * Create a table for this pojo on the given {@link Database}
	 *
	 * @param database The {@link Database} to create the table on
	 * @throws SQLException If something goes wrong in creating the table
	 */
	default void createTable(Database database) throws SQLException{
		// Start the create statement
		SQLCreateStatement.ForeignKeysOrBuild createStmt = SQLCreateStatement.builder()
				.table()
				.tableName(getTableName())
				.columns(new ArrayList<>(getColumnDefs().values()));
		
		// Add foreign key constraints
		for(ForeignKeyConstraint foreignKey: getForeignKeys()){
			createStmt.foreignKey(foreignKey);
		}
		
		// Execute the update on the database
		database.executeUpdate("Create " + getTableName(), createStmt.build().toString());
	}
	
	/**
	 * Retrieves all the values for the pojo from the database, using the stored id column value
	 *
	 * @param database The {@link Database} to retrieve values from
	 * @param retrieveSubPojos Whether to also retrieve values for subPojos or not
	 * @throws SQLException If anything goes wrong in retrieving values
	 */
	default void retrieveValues(Database database, boolean retrieveSubPojos) throws SQLException{
		retrieveValues(database, getItem(getIDColumnName()), retrieveSubPojos);
	}
	
	/**
	 * Retrieves all the values for the pojo from the database, using the given id column value
	 *
	 * @param database The {@link Database} to retrieve values from
	 * @param idColumnValue The ID column value to be used
	 * @param retrieveSubPojos Whether to also retrieve values for subPojos or not
	 * @throws SQLException If anything goes wrong in retrieving values
	 */
	default void retrieveValues(Database database, Object idColumnValue, boolean retrieveSubPojos) throws SQLException{
		database.executeQuery("Retrieve " + getTableName(),
				SQLSyntaxUtil.formatQuery(ListUtil.createList(getTableName()), getColumnDefKeys(),
						ListUtil.createList(getIDColumnName()), ListUtil.createList(idColumnValue), false),
				getResultSetFunc());
		
		// Retrieve sub pojos if we have them and it's specified
		if(retrieveSubPojos){
			Set<String> subPojoKeys = getSubPojoKeys();
			if(!subPojoKeys.isEmpty()){
				for(String subPojoKey: subPojoKeys){
					DatabasePojo subPojo = (DatabasePojo) getItem(subPojoKey);
					if(subPojo != null){
						subPojo.retrieveValues(database, true);
					}else{
						SubPojoDefinition subPojoDef = getSubPojoDefBySubPojoKey(subPojoKey);
						String subPojoIDCol = subPojoDef.getIDCol();
						Class<? extends DatabasePojo> clazz = subPojoDef.getType();
						if(StringUtil.isNotBlank(subPojoIDCol) && clazz != null){
							try{
								subPojo = clazz.getConstructor().newInstance();
								setItem(subPojoKey, subPojo);
								subPojo.retrieveValues(database, getItem(subPojoIDCol), true);
							}catch(InvocationTargetException | InstantiationException |
									IllegalAccessException | NoSuchMethodException e){
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * @return A {@link ThrowingFunction} to use to extract a single {@link DatabasePojo} from a {@link ResultSet}
	 */
	default ThrowingFunction<ResultSet, Boolean, SQLException> getResultSetFunc(){
		String tableName = getTableName();
		return resultSet -> {
			resultSet.next();
			Map<String, ColumnDefinition> columnDefs = getColumnDefs();
			for(String columnDefKey: getColumnDefKeys()){
				setItem(columnDefKey, SQLSyntaxUtil.getValueBasedOnColumnDefinition(resultSet, tableName,
						columnDefs.get(columnDefKey)));
			}
			return true;
		};
	}
	
	/**
	 * This {@link ThrowingFunction} is used to extract a List of {@link DatabasePojo DatabasePojos} from a
	 * {@link ResultSet} (e.g. from a search).
	 *
	 * @param clazz The {@link DatabasePojo} class to be used for the List
	 * @param <P> The {@link DatabasePojo} class to be used for the List
	 * @return A {@link ThrowingFunction} that extracts a List of {@link DatabasePojo DatabasePojos} from a
	 * {@link ResultSet}
	 */
	default <P extends DatabasePojo> ThrowingFunction<ResultSet, List<P>, SQLException> getResultSetListFunc(
			Class<P> clazz){
		Map<String, ColumnDefinition> columnDefs = getColumnDefs();
		Set<String> columnDefKeys = getColumnDefKeys();
		String tableName = getTableName();
		return resultSet -> {
			List<P> pojos = new ArrayList<>();
			while(resultSet.next()){
				try{
					P pojo = clazz.getConstructor().newInstance();
					for(String columnDefKey: columnDefKeys){
						pojo.setItem(columnDefKey, SQLSyntaxUtil.getValueBasedOnColumnDefinition(resultSet,
								tableName, columnDefs.get(columnDefKey)));
					}
					pojos.add(pojo);
				}catch(InstantiationException | IllegalAccessException |
						InvocationTargetException | NoSuchMethodException e){
					e.printStackTrace();
				}
			}
			return pojos;
		};
	}
	
	/**
	 * Stores the values from this {@link DatabasePojo} into the given {@link Database}. Specifying storeSubPojos
	 * will also store values on any subPojos stored in this pojo
	 *
	 * @param database The {@link Database} to store values in
	 * @param storeSubPojos Whether to store values on subPojos or not
	 * @throws SQLException If anything goes wrong in storing values
	 */
	default Integer storeValues(Database database, boolean storeSubPojos) throws SQLException{
		List<String> columnDefKeys = getColumnDefKeys().stream()
				.filter(colDefKey -> !StringUtil.equalsIgnoreCase(colDefKey, getIDColumnName()))
				.collect(Collectors.toList());
		List<Object> values = columnDefKeys.stream().map(this::getItem).collect(Collectors.toList());
		Object id = getItem(getIDColumnName());
		Integer newID = null;
		if(id == null){
			newID = database.insertAndGetID(getTableName(), getIDColumnName(), columnDefKeys, values);
			setItem(getIDColumnName(), newID);
		}else{
			database.update(getTableName(), columnDefKeys, values,
					ListUtil.createList(getIDColumnName()), ListUtil.createList(id));
		}
		
		// Store any sub pojos if they exist and it's specified
		if(storeSubPojos){
			Set<String> subPojoKeys = getSubPojoKeys();
			if(!subPojoKeys.isEmpty()){
				for(String subPojoKey: subPojoKeys){
					DatabasePojo subPojo = (DatabasePojo) getItem(subPojoKey);
					if(subPojo != null){
						Integer subPojoID = subPojo.storeValues(database, true);
						String subPojoIDCol = getSubPojoDefBySubPojoKey(subPojoKey).getIDCol();
						if(subPojoID != null && StringUtil.isNotBlank(subPojoIDCol)){
							setItem(subPojoIDCol, subPojoID);
						}
					}
				}
			}
		}
		return newID;
	}
	
	/**
	 * Performs a search for data on pojos of this type in the given {@link Database}. The given class is for
	 * which class of pojos to return and the boolean determines whether to use all the subPojos stored in this
	 * pojo {@code true} in this search or use none of them {@code false}
	 *
	 * @param database The {@link Database} to do the search on
	 * @param clazz The {@link DatabasePojo} class to use for the returned pojos
	 * @param useSubPojos Whether to use all subPojos {@code true} or none {@code false} in the search
	 * @param <P> The {@link DatabasePojo} class to use for the returned pojos
	 * @return A List of pojos found from the search
	 * @throws SQLException If anything goes wrong in doing the search
	 */
	default <P extends DatabasePojo> List<P> doSearch(Database database, Class<P> clazz, boolean useSubPojos)
			throws SQLException{
		return useSubPojos?doSearch(database, clazz, getSubPojoKeys()):doSearch(database, clazz, new ArrayList<>());
	}
	
	/**
	 * Performs a search for data on pojos of this type in the given {@link Database}. The given class is for
	 * which class of pojos to return and the collection of subPojo strings is for subPojoKeys of subPojos to
	 * be used in the search (so long as they're non-null)
	 *
	 * @param database The {@link Database} to do the search on
	 * @param clazz The {@link DatabasePojo} class to use for the returned pojos
	 * @param subPojosToUse The subPojoKeys of subPojos to use in the search
	 * @param <P> The {@link DatabasePojo} class to use for the returned pojos
	 * @return A List of pojos found from the search
	 * @throws SQLException If anything goes wrong in doing the search
	 */
	default <P extends DatabasePojo> List<P> doSearch(
			Database database, Class<P> clazz, Collection<String> subPojosToUse) throws SQLException{
		// Setup the collections we'll be using for the search
		Set<String> columnDefKeys = getColumnDefKeys();
		List<String> tables = ListUtil.createList(getTableName());
		List<String> columnsToReturn = new ArrayList<>();
		List<String> columnDefsToUse = new ArrayList<>();
		List<Object> valuesToUse = new ArrayList<>();
		
		// Add columns and values based on non-null values in this pojo
		String table = getTableName();
		for(String columnDefKey: columnDefKeys){
			String columnName = table + "." + columnDefKey;
			columnsToReturn.add(columnName);
			Object value = getItem(columnDefKey);
			if(value != null){
				columnDefsToUse.add(columnName);
				valuesToUse.add(value);
			}
		}
		
		// Use subPojos if we have them
		if(!subPojosToUse.isEmpty()){
			for(String subPojoKey: subPojosToUse){
				DatabasePojo subPojo = (DatabasePojo) getItem(subPojoKey);
				if(subPojo != null){
					// Add the subPojo's table to the list
					String subPojoTable = subPojo.getTableName();
					
					// Check for values on the subPojo to include and track if we use it
					boolean useSubPojo = false;
					Set<String> subPojoColDefKeys = subPojo.getColumnDefKeys();
					for(String columnDefKey: subPojoColDefKeys){
						Object value = subPojo.getItem(columnDefKey);
						if(value != null){
							columnDefsToUse.add(subPojoTable + "." + columnDefKey);
							valuesToUse.add(value);
							useSubPojo = true;
						}
					}
					
					// If we're using this subPojo, add its table and junction to the collections
					if(useSubPojo){
						tables.add(subPojoTable);
						Pair<ColumnRef, ColumnRef> junction = getSubPojoDefBySubPojoKey(subPojoKey).getJunction();
						columnDefsToUse.add(junction.getLeft().toString());
						valuesToUse.add(junction.getRight());
					}
				}
			}
		}
		
		// Form the query and run it
		String sql = SQLSyntaxUtil.formatQuery(tables, columnsToReturn, columnDefsToUse, valuesToUse, true);
		return database.executeQuery("Search for " + clazz.getName(), sql, getResultSetListFunc(clazz));
	}
}
