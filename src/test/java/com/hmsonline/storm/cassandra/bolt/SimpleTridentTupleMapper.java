package com.hmsonline.storm.cassandra.bolt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.tuple.TridentTuple;
import backtype.storm.tuple.Fields;

import com.hmsonline.storm.cassandra.bolt.mapper.TridentTupleMapper;
import com.hmsonline.storm.cassandra.exceptions.TupleMappingException;

public class SimpleTridentTupleMapper implements TridentTupleMapper<String, String, String> {
	private static final Logger LOG = LoggerFactory.getLogger(SimpleTridentTupleMapper.class);

    private static final long serialVersionUID = 6362052836181968031L;

    private Fields fields;
    private String keyspace;

    public SimpleTridentTupleMapper(String keyspace, Fields fields){
        this.fields = fields;
        this.keyspace = keyspace;
    }

    @Override
    public String mapToColumnFamily(TridentTuple tuple) throws TupleMappingException {
        return "trident";
    }
    
    @Override
    public String mapToKeyspace(TridentTuple tuple) {
        return this.keyspace;
    }

    @Override
    public String mapToRowKey(TridentTuple tuple) throws TupleMappingException {
        return tuple.getStringByField("key1");
    }

    @Override
    public Map<String, String> mapToColumns(TridentTuple tuple) throws TupleMappingException {
        HashMap<String, String> retval = new HashMap<String, String>();
        for(String field : this.fields.toList()){
        	String value = tuple.getStringByField(field);
//        	LOG.debug("Field: {}, Value:{}", field, value);
            retval.put(field, value);
        }
//        LOG.debug("{}", retval);
        return retval;
    }
    
    @Override
    public List<String> mapToColumnsForLookup(TridentTuple tuplex) {
        throw new RuntimeException("Should not be called since this mapper is not used to read data.");
    }

    @Override
    public String mapToEndKey(TridentTuple tuple) throws TupleMappingException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String mapToStartKey(TridentTuple tuple) throws TupleMappingException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean shouldDelete(TridentTuple tuple) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Class<String> getKeyClass() {
        return String.class;
    }

    @Override
    public Class<String> getColumnNameClass() {
        return String.class;
    }

    @Override
    public Class<String> getColumnValueClass() {
        return String.class;
    }

}
