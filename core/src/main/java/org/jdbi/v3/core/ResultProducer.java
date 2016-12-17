/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Supplier;

/**
 * Produces a result from an executed {@link PreparedStatement}.
 *
 * @param <R> Result type
 */
@FunctionalInterface
public interface ResultProducer<R> {
    /**
     * Produces a statement result. The statement is not executed until {@code statementSupplier.get()} is called.
     * <p>
     * Implementors that call {@code statementSupplier.get()} must ensure that the statement context is closed before
     * returning, to ensure that database resources are freed:
     * <pre>
     * try {
     *     PreparedStatement statement = statementSupplier.get()
     *     // generate and return result from the statement
     * }
     * finally {
     *     ctx.close();
     * }
     * </pre>
     * <p>
     * Alternatively, implementors may return some intermediate result object (e.g. {@link ResultSetIterable} or
     * {@link ResultIterable}) without calling {@code statementSupplier.get()}, in which case the burden of closing
     * resources falls to whichever object ultimately does {@code get()} the statement.
     *
     * @param statementSupplier supplies a PreparedStatement, post-execution.
     * @param ctx               the statement context
     * @return an object of the type your caller expects
     * @throws SQLException sadness
     */
    R produce(Supplier<PreparedStatement> statementSupplier, StatementContext ctx) throws SQLException;
}
