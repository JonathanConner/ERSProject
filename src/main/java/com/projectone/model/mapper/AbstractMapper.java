package com.projectone.model.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.LogManager;

import com.projectone.model.DomainObject;
import com.projectone.model.User;
import com.projectone.util.ConnectionUtil;

public abstract class AbstractMapper {
	
	protected Logger logger;
	
	protected abstract String findStatement();
	
	private Map loadedMap = new HashMap();

	public abstract DomainObject find(long id);

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
		
		return null;
	}
	
	
	protected DomainObject load(ResultSet rs) throws SQLException {
		
		long id = rs.getLong(1);

		// If the loadedMap contains a key of ID return that value
		if(loadedMap.containsKey(id)) return (DomainObject) loadedMap.get(id); 
		
		DomainObject result = doLoad(id, rs);
	
		doRegister(id, result);
		logger.info(this.getClass() + ": Added to the loaded HashMap<long, DomainObject<"+result.getClass().getSimpleName()+">> ");
		return result;
		
	}


	protected void doRegister(long id, DomainObject result) {
		if(loadedMap.containsKey(id))
			loadedMap.put(id, result);
	}


	
	protected abstract DomainObject doLoad(long id, ResultSet rs) throws SQLException;
	
	
	public abstract void update(DomainObject arg);
	

	
	
	
}
