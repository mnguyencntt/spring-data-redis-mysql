package com.anz.platform.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.anz.platform.domain.BaseObject;
import com.anz.platform.domain.Student;
import com.anz.platform.util.Constants;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Service
public class StudentService implements AutoCloseable {
  @Value("${spring.datasource.url}")
  private String endpoint;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  private Connection connection;

  @PostConstruct
  public void init() throws SQLException {
    log.info(Constants.INITIALIZE_CONNECTION);
    this.connection = DriverManager.getConnection(endpoint, username, password);
    log.info(Constants.SUCCESSFUL_CONNECTION);
  }

  @Override
  public void close() throws Exception {
    log.info(Constants.CLOSE_CONNECTION);
    DbUtils.close(this.connection);
  }

  public <T> List<T> findAll(Class<T> clazz) throws SQLException {
    final ResultSetHandler<List<T>> resultHandler = new BeanListHandler<>(clazz);
    final String selectAll = String.format("SELECT * FROM %s", clazz.getSimpleName());
    final List<T> items = new QueryRunner().query(connection, selectAll, resultHandler);
    log.info("Found {} Items", items.size());
    return items;
  }

  public <T> T findById(final String id, Class<T> clazz) throws SQLException {
    final ResultSetHandler<T> resultHandler = new BeanHandler<>(clazz);
    final String selectById = String.format("SELECT * FROM %s WHERE id = ?", clazz.getSimpleName());
    T query = new QueryRunner().query(connection, selectById, resultHandler, id);
    log.info("Found Item by '{}'", id);
    return query;
  }

  public <T> T findByField(final String fieldName, final String fieldValue, Class<T> clazz) throws SQLException {
    final ResultSetHandler<T> resultHandler = new BeanHandler<>(clazz);
    final String selectByField = String.format("SELECT * FROM %s WHERE %s = ?", clazz.getSimpleName(), fieldName);
    T query = new QueryRunner().query(connection, selectByField, resultHandler, fieldValue);
    log.info("Found Item by '{}'", fieldValue);
    return query;
  }

  // INSERT INTO table_name (column1, column2, column3, ...)
  // VALUES (value1, value2, value3, ...);
  public <T extends BaseObject> Integer persist(final T item) throws SQLException {
    final String insertSQL =
        String.format("INSERT INTO %s (%s) VALUES (%s)", item.getClass().getSimpleName(), item.findFieldsJoining(), item.findMarksJoining());
    log.info(insertSQL);
    final Object[] inputValues = item.findValues();
    return new QueryRunner().update(connection, insertSQL, inputValues);
  }

  // INSERT INTO table_name (column1, column2, column3, ...)
  // VALUES (value1, value2, value3, ...);
  public <T extends BaseObject> Integer persist(final List<T> items) throws SQLException {
    Integer countPersisted = 0;
    for (T item : items) {
      final String insertSQL =
          String.format("INSERT INTO %s (%s) VALUES (%s)", item.getClass().getSimpleName(), item.findFieldsJoining(), item.findMarksJoining());
      log.info(insertSQL);
      final Object[] inputValues = item.findValues();
      final Integer persisted = new QueryRunner().update(connection, insertSQL, inputValues);
      countPersisted += persisted;
    }
    return countPersisted;
  }

  // UPDATE table_name
  // SET column1 = value1, column2 = value2, ...
  // WHERE condition;
  public Integer updateById(final Student item) throws SQLException {
    final String updateSQL =
        String.format("Update %s SET %s WHERE Id='%s'", item.getClass().getSimpleName(), item.findFieldValuesJoining("Id"), item.getId());
    log.info(updateSQL);
    return new QueryRunner().update(connection, updateSQL);
  }

  public <T> Integer deleteInIds(final List<String> ids, Class<T> clazz) throws SQLException {
    final String inIdsParam = ids.stream().map(id -> Constants.QUOTE + id + Constants.QUOTE).collect(Collectors.joining(Constants.COMMA));
    final String deleteInIds = String.format("DELETE FROM %s WHERE id IN (%s)", clazz.getSimpleName(), inIdsParam);
    final Integer deletedRecords = new QueryRunner().update(connection, deleteInIds);
    log.info("'{}' record(s) deleted.", deletedRecords);
    return deletedRecords;
  }

  public <T> Integer deleteInKioskIds(final List<String> kioskIds, Class<T> clazz) throws SQLException {
    final String inIdsParam = kioskIds.stream().map(id -> Constants.QUOTE + id + Constants.QUOTE).collect(Collectors.joining(Constants.COMMA));
    final String deleteInIds = String.format("DELETE FROM %s WHERE kioskId IN (%s)", clazz.getSimpleName(), inIdsParam);
    final Integer deletedRecords = new QueryRunner().update(connection, deleteInIds);
    log.info("'{}' record(s) deleted.", deletedRecords);
    return deletedRecords;
  }
}
