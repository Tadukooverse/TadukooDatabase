package com.github.tadukoo.database.mysql.pojo;

import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.ForeignKeyConstraint;
import com.github.tadukoo.util.pojo.MappedPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract Database Pojo is a simple implementation of a {@link DatabasePojo} to use to easily make pojos that
 * can be stored and retrieved from a {@link Database}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public abstract class AbstractDatabasePojo implements DatabasePojo{
	/** The Map of items in this pojo */
	private final Map<String, Object> itemMap;
	/** The Map of {@link ColumnDefinition column definitions} for this pojo */
	private final Map<String, ColumnDefinition> columnDefMap;
	/** The Map of {@link SubPojoDefinition subPojo definitions} for this pojo */
	private final Map<String, SubPojoDefinition> subPojoDefs;
	/** The List of any {@link ForeignKeyConstraint foreign keys} present for this pojo */
	private final List<ForeignKeyConstraint> foreignKeys;
	
	/**
	 * Constructs a new {@link AbstractDatabasePojo} with an empty itemMap and columnDefMap, and calls
	 * {@link #setDefaultColumnDefs()}
	 */
	protected AbstractDatabasePojo(){
		itemMap = new HashMap<>();
		columnDefMap = new HashMap<>();
		subPojoDefs = new HashMap<>();
		foreignKeys = new ArrayList<>();
		setDefaultColumnDefs();
	}
	
	/**
	 * Constructs a new {@link AbstractDatabasePojo} with the itemMap from the given {@link MappedPojo},
	 * an empty columnDefMap, and calls {@link #setDefaultColumnDefs()}
	 *
	 * @param pojo The {@link MappedPojo} to use for its itemMap
	 */
	protected AbstractDatabasePojo(MappedPojo pojo){
		itemMap = pojo.getMap();
		columnDefMap = new HashMap<>();
		subPojoDefs = new HashMap<>();
		foreignKeys = new ArrayList<>();
		setDefaultColumnDefs();
	}
	
	/** {@inheritDoc} */
	@Override
	public Map<String, Object> getMap(){
		return itemMap;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Map<String, ColumnDefinition> getColumnDefs(){
		return columnDefMap;
	}
	
	/** {@inheritDoc} */
	@Override
	public Map<String, SubPojoDefinition> getSubPojoDefs(){
		return subPojoDefs;
	}
	
	/** {@inheritDoc} */
	@Override
	public List<ForeignKeyConstraint> getForeignKeys(){
		return foreignKeys;
	}
}
