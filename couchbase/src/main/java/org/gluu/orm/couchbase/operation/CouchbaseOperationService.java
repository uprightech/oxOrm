/*
 * oxCore is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2014, Gluu
 */

package org.gluu.orm.couchbase.operation;

import java.util.List;

import org.gluu.orm.couchbase.impl.CouchbaseBatchOperationWraper;
import org.gluu.orm.couchbase.model.SearchReturnDataType;
import org.gluu.orm.couchbase.operation.impl.CouchbaseConnectionProvider;
import org.gluu.persist.exception.operation.DeleteException;
import org.gluu.persist.exception.operation.DuplicateEntryException;
import org.gluu.persist.exception.operation.EntryNotFoundException;
import org.gluu.persist.exception.operation.PersistenceException;
import org.gluu.persist.exception.operation.SearchException;
import org.gluu.persist.model.PagedResult;
import org.gluu.persist.model.SearchScope;
import org.gluu.persist.operation.PersistenceOperationService;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.consistency.ScanConsistency;
import com.couchbase.client.java.query.dsl.Expression;
import com.couchbase.client.java.query.dsl.Sort;
import com.couchbase.client.java.subdoc.MutationSpec;

/**
 * Couchbase operation service interface
 *
 * @author Yuriy Movchan Date: 05/14/2018
 */
public interface CouchbaseOperationService extends PersistenceOperationService {

    static String DN = "dn";
    static String UID = "uid";
    static String[] UID_ARRAY = new String[] { "uid" };
    static String USER_PASSWORD = "userPassword";
    static String OBJECT_CLASS = "objectClass";

    static String META_DOC_ID = "meta_doc_id";

    CouchbaseConnectionProvider getConnectionProvider();

    boolean addEntry(String key, JsonObject atts) throws DuplicateEntryException, PersistenceException;
	boolean addEntry(String key, JsonObject jsonObject, Integer expiration) throws DuplicateEntryException, PersistenceException;

    boolean updateEntry(String key, List<MutationSpec> mods, Integer expiration) throws UnsupportedOperationException, PersistenceException;

    boolean delete(String key) throws EntryNotFoundException;
	int delete(String key, ScanConsistency scanConsistency, Expression expression, int count) throws DeleteException;
    boolean deleteRecursively(String key) throws EntryNotFoundException, SearchException;

    JsonObject lookup(String key, ScanConsistency scanConsistency, String... attributes) throws SearchException;

    <O> PagedResult<JsonObject> search(String key, ScanConsistency scanConsistency, Expression expression, SearchScope scope,
            String[] attributes, Sort[] orderBy, CouchbaseBatchOperationWraper<O> batchOperationWraper, SearchReturnDataType returnDataType,
            int start, int count, int pageSize) throws SearchException;

    String[] createStoragePassword(String[] passwords);

    boolean isBinaryAttribute(String attribute);
    boolean isCertificateAttribute(String attribute);

    boolean destroy();

}
