package com.github.tadukoo.database.mysql;

/**
 *
 *
 * @version Alpha v.0.3
 * @since Alpha v.0.1
 */
public class DBUtil{
	
	public static void addConditionalAndToQuery(boolean prevSet, StringBuilder name, StringBuilder sql){
		if(prevSet){
			name.append(" and ");
			sql.append(" and ");
		}
	}
	
	public static boolean addConditionalIntToQuery(boolean prevSet, StringBuilder name, StringBuilder sql, String arg, int value){
		if(value != -1){
			addConditionalAndToQuery(prevSet, name, sql);
			name.append(arg).append(" of ").append(value);
			sql.append(arg).append(" = ").append(value);
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean addConditionalStringToQuery(boolean prevSet, StringBuilder name, StringBuilder sql, boolean partial, 
			String arg, String value){
		if(value != null && !value.equalsIgnoreCase("")){
			addConditionalAndToQuery(prevSet, name, sql);
			if(partial){
				name.append(arg).append(" with ").append(value);
				sql.append(arg).append(" like '%").append(value).append("%'");
			}else{
				name.append(arg).append(" of ").append(value);
				sql.append(arg).append(" = '").append(value).append("'");
			}
			return true;
		}else{
			return false;
		}
	}
	
	public static void addJunctionStringToQuery(boolean prevSet, StringBuilder name, StringBuilder sql, String junction){
		addConditionalAndToQuery(prevSet, name, sql);
		name.append(junction);
		sql.append(junction);
	}
	
	// TODO: Improve (/Rework doSearch logic into here)
	public static String formatQuery(String table, String returnPieces, String[] args, String[] values){
		StringBuilder selectSQL = new StringBuilder("select " + returnPieces + " from " + table + " where ");
		for(int i = 0; i < args.length; i++){
			if(!values[i].startsWith("SHA")){
				selectSQL.append(args[i]).append(" = ");
				if(values[i].equalsIgnoreCase("true") || values[i].equalsIgnoreCase("false")){
					selectSQL.append(values[i]);
				}else{
					selectSQL.append("'").append(values[i]).append("'");
				}
				if(i != args.length - 1){
					selectSQL.append(" and ");
				}else{
					selectSQL.append(";");
				}
			}
		}
		return selectSQL.toString();
	}
	
	public static String formatInsertStatement(String table, String[] args, String[] values){
		StringBuilder insertSQL = new StringBuilder("insert into " + table + " (");
		for(int i = 0; i < args.length; i++){
			if(i == args.length - 1){
				insertSQL.append(args[i]).append(") select ");
			}else{
				insertSQL.append(args[i]).append(", ");
			}
		}
		for(int i = 0; i < values.length; i++){
			if(values[i].equalsIgnoreCase("true") || values[i].equalsIgnoreCase("false") ||
					values[i].startsWith("SHA")){
				insertSQL.append(values[i]);
			}else{
				insertSQL.append("'").append(values[i]).append("'");
			}
			if(i == values.length - 1){
				insertSQL.append(";");
			}else{
				insertSQL.append(", ");
			}
		}
		return insertSQL.toString();
	}
}
