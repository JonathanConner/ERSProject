package com.projectone.model.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.projectone.model.DomainObject;
import com.projectone.util.ConnectionUtil;

public abstract class AbstractMapper {
	
	private Logger logger;
	
	protected abstract String findStatement();
	
	private Map loadedMap = new HashMap<>();

	
	protected DomainObject abstractFind(Long id) {
		DomainObject result = (DomainObject) loadedMap.get(id);
		if(result != null) return result;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()){
			stmt = conn.prepareStatement(findStatement());
			stmt.setLong(1, id);
			
			rs = stmt.executeQuery();
			
			rs.next();
			
			result = load(rs);
			return result;
			
		} catch(SQLException sqle) {
			logger.warn(sqle);
		}
			
	}
	
	
	protected DomainObject load(ResultSet rs) throws SQLException {
		
		Long id = new Long(rs.getLong(1));

		if(loadedMap.containsKey(id)) return (DomainObject) loadedMap.get(id);
		
		DomainObject result = doLoad(id, rs);
		
	}
	
	
}
