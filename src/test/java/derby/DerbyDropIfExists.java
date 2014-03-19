/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Registered as stored procedure for Apache Derby database. It's a workaround
 * for missing "DROP IF EXISTS" feature.
 * 
 * The main rationale is to have repeatable tests where you can create
 * drop/create tables safely.
 */
public class DerbyDropIfExists {

    public static void dropTable(String schema, String table) {
        executeStatementIgnoreOnException("drop table " + schema + "." + table);
    }

    public static void dropSequence(String schema, String sequence) {
        executeStatementIgnoreOnException("drop sequence " + schema + "." + sequence + " restrict");
    }

    private static void executeStatementIgnoreOnException(String statement) {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:target/derbydb");
                PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            // IGNORE - it fails when you try to drop non-existing table, but we
            // want to continue
        }

    }

}
