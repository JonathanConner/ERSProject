package com.projectone.util;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.projectone.model.Reimbursement;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONConverter {
	
	public static String writeListToJsonArray(List<Reimbursement> reimbs) throws IOException {  

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
	    final ObjectMapper mapper = new ObjectMapper();

	    mapper.writeValue(out, reimbs);

	    final byte[] data = out.toByteArray();
	    StringBuilder sb = new StringBuilder();
	    sb.append("{\"data\":");
	    sb.append(new String(data));
	    sb.append("}");
	    return sb.toString();
	}
	
	public static String writeListToJsonArrayWithButtons(List<Reimbursement> reimbs) throws IOException {  

		reimbs.forEach(e->{e.setButtons();});
		
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
	    final ObjectMapper mapper = new ObjectMapper();

	    mapper.writeValue(out, reimbs);

	    final byte[] data = out.toByteArray();
	    StringBuilder sb = new StringBuilder();
	    sb.append("{\"data\":");
	    sb.append(new String(data));
	    sb.append("}");
	    return sb.toString();
	}
	
}
