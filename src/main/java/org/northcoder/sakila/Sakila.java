package org.northcoder.sakila;

import java.sql.Connection;
import java.sql.DriverManager;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.conf.UpdateUnchangedRecords;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sakila {

    private static DSLContext dsl;
    private final Logger LOGGER = LoggerFactory.getLogger(Sakila.class);

    //
    // the Sakila database I am using (the MySQL flavor)
    // https://www.jooq.org/sakila
    //
    // a handy online translation tool - including translation from
    // supported dialects of SQL to jOOQ's Java - which is otherwise a
    // premium (paid for) feature:
    // https://www.jooq.org/translate/
    //
    // respect!:
    // https://www.jooq.org/doc/latest/manual/sql-building/sql-parser/sql-parser-grammar/
    //
    public static void main(String[] args) {
        Sakila sakila = new Sakila();
        sakila.LOGGER.info("Starting...");
        // see https://blog.jooq.org/mysqls-allowmultiqueries-flag-with-jdbc-and-jooq/
        // for the reason why I have allowMultiQueries=true in the URL here:
        String url = "jdbc:mysql://localhost:3306/sakila?allowMultiQueries=true";
        String user = "sakilauser";
        String pass = "sakilapass";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            Settings settings = new Settings()
                    .withExecuteWithOptimisticLocking(true)
                    .withInsertUnchangedRecords(false)
                    .withUpdateUnchangedRecords(UpdateUnchangedRecords.NEVER);

            Configuration config = new DefaultConfiguration()
                    .set(conn)
                    .set(SQLDialect.MYSQL)
                    .set(settings);

            dsl = DSL.using(config);
            sakila.jooqExamples();

        } catch (Exception e) {
            sakila.LOGGER.error("Exception", e);
        }

    }

    private void jooqExamples() {
        JooqExamples examples = new JooqExamples(dsl);
        // some beginner/concepts code examples:
        examples.fetchingExactlyOneRecord();
        examples.fetchingZeroOneOrManyRecords();
        examples.basicProjections();
        examples.parentChildNavigation();
        examples.fetchingIntoPojos();
        examples.pathJoining();
        examples.multisets();
        examples.nestedJavaRecords();
        examples.updating();
        examples.insertingAndDeleting();
        examples.pathCorrelation();
        examples.aliasing();
        examples.commonJooqTypes();
        examples.dao();
        examples.plainSql();
    }

}
