package org.simple.jsontest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;

public class Try2 {
	public	static void main(String[] args) {
		{
			ProcessData pd1 = new ProcessData();
			StructuredMeta sm1 = new StructuredMeta();
			sm1.setTableName("tb_foo");
			pd1.setKind("Structured");
			pd1.setMeta(sm1);
			
			Gson gson = new Gson();
			String s = gson.toJson(pd1);

			System.out.println(s);

			ProcessData pd2 = null;
			try {
				pd2 = gson.fromJson(s, ProcessData.class);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (pd2 != null) {
				System.out.println(pd2.getKind()+"::"+((StructuredMeta)pd2.getMeta()).getTableName());
			}
		}
		
		{
			ProcessData pd1 = new ProcessData();
			StructuredMeta sm1 = new StructuredMeta();
			sm1.setTableName("tb_foo");
			pd1.setKind("Structured");
			pd1.setMeta(sm1);
			
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Meta.class, new InterfaceAdapter<Meta>());
			Gson gson = builder.create();
			String s = gson.toJson(pd1);
			
			System.out.println(s);

			ProcessData pd2 = gson.fromJson(s, ProcessData.class);
			StructuredMeta sm2 = (StructuredMeta) pd2.getMeta();
			System.out.println(pd2.getKind()+"::"+sm2.getTableName());
		}
		
		{
			ProcessData pd1 = new ProcessData();
			StructuredMeta sm1 = new StructuredMeta();
			sm1.setTableName("tb_foo");
			StructuredMetaItem smi11 = new StructuredMetaItem();
			smi11.setColumnName("foo_id");
			sm1.addItem(smi11);
			StructuredMetaItem smi12 = new StructuredMetaItem();
			smi12.setColumnName("foo_name");
			sm1.addItem(smi12);
			pd1.setKind("Structured");
			pd1.setMeta(sm1);
			
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Meta.class, new InterfaceAdapter<Meta>());
			//builder.registerTypeAdapter(MetaItem.class, new InterfaceAdapter<MetaItem>());
			Gson gson = builder.create();
			String s = gson.toJson(pd1);
			
			System.out.println(s);

			ProcessData pd2 = gson.fromJson(s, ProcessData.class);
			StructuredMeta sm2 = (StructuredMeta) pd2.getMeta();
			StructuredMetaItem smi21 = sm2.getItems().get(0);
			//StructuredMetaItem smi21 = (StructuredMetaItem) sm2.getItems().get(0);
			System.out.println(pd2.getKind()+"::"+sm2.getTableName()+"::"+smi21.getColumnName());
		}
	}
}

class ProcessData {
	private String kind;
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	private Meta meta;
	public Meta getMeta() {
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}

interface Meta {
}

class StructuredMeta implements Meta {
	private String tableName;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	//*
	private List<StructuredMetaItem> items;
	public List<StructuredMetaItem> getItems() {
		return items;
	}
	public void setItems(List<StructuredMetaItem> items) {
		this.items = items;
	}
	public void addItem(StructuredMetaItem item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
	}
	//*/
	/*
	private List<MetaItem> items;
	public List<MetaItem> getItems() {
		return items;
	}
	public void setItems(List<MetaItem> items) {
		this.items = items;
	}
	public void addItem(MetaItem item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
	}
	//*/
}

class UnstructuredMeta implements Meta {
	private String bucketName;
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	//*
	private List<UnstructuredMetaItem> items;
	public List<UnstructuredMetaItem> getItems() {
		return items;
	}
	public void setItems(List<UnstructuredMetaItem> items) {
		this.items = items;
	}
	public void addItem(UnstructuredMetaItem item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
	}
	//*/
	/*
	private List<MetaItem> items;
	public List<MetaItem> getItems() {
		return items;
	}
	public void setItems(List<MetaItem> items) {
		this.items = items;
	}
	public void addItem(MetaItem item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
	}
	//*/
}

interface MetaItem {
}

class StructuredMetaItem implements MetaItem {
	private String columnName;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}

class UnstructuredMetaItem implements MetaItem {
	private String attributeName;
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
}

// from
// https://technology.finra.org/code/serialize-deserialize-interfaces-in-java.html
class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
	
	private static final String CLASSNAME = "CLASSNAME";
	private static final String DATA = "DATA";
	
	public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
		String className = prim.getAsString();
		Class klass = getObjectClass(className);
		return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
	}
	
	public JsonElement serialize(T jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
		jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
		return jsonObject;
	}
	
	/****** Helper method to get the className of the object to be deserialized *****/
	public Class getObjectClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			throw new JsonParseException(e.getMessage());
		}
	}
}
