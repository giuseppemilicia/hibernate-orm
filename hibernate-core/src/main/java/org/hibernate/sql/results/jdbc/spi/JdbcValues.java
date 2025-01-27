/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sql.results.jdbc.spi;

import org.hibernate.engine.spi.SharedSessionContractImplementor;

/**
 * Provides unified access to query results (JDBC values - see
 * {@link RowProcessingState#getJdbcValue} whether they come from
 * query cache or ResultSet.  Implementations also manage any cache puts
 * if required.
 *
 * @author Steve Ebersole
 */
public interface JdbcValues {
	JdbcValuesMapping getValuesMapping();

	/**
	 * Advances the "cursor position" and returns a boolean indicating whether
	 * there is a row available to read via {@link #getCurrentRowValuesArray()}.
	 *
	 * @return {@code true} if there are results
	 */
	boolean next(RowProcessingState rowProcessingState);

	/**
	 * Advances the "cursor position" in reverse and returns a boolean indicating whether
	 * there is a row available to read via {@link #getCurrentRowValuesArray()}.
	 *
	 * @return {@code true} if there are results available
	 */
	boolean previous(RowProcessingState rowProcessingState);

	/**
	 * Advances the "cursor position" the indicated number of rows and returns a boolean
	 * indicating whether there is a row available to read via {@link #getCurrentRowValuesArray()}.
	 *
	 * @param numberOfRows The number of rows to advance.  This can also be negative meaning to
	 * move in reverse
	 *
	 * @return {@code true} if there are results available
	 */
	boolean scroll(int numberOfRows, RowProcessingState rowProcessingState);

	/**
	 * Moves the "cursor position" to the specified position
	 */
	boolean position(int position, RowProcessingState rowProcessingState);

	int getPosition();

	boolean isBeforeFirst(RowProcessingState rowProcessingState);
	void beforeFirst(RowProcessingState rowProcessingState);

	boolean isFirst(RowProcessingState rowProcessingState);
	boolean first(RowProcessingState rowProcessingState);

	boolean isAfterLast(RowProcessingState rowProcessingState);
	void afterLast(RowProcessingState rowProcessingState);

	boolean isLast(RowProcessingState rowProcessingState);
	boolean last(RowProcessingState rowProcessingState);

	/**
	 * Get the JDBC values for the row currently positioned at within
	 * this source.
	 *
	 * @return The current row's JDBC values, or {@code null} if the position
	 * is beyond the end of the available results.
	 */
	Object[] getCurrentRowValuesArray();

	/**
	 * Give implementations a chance to finish processing
	 */
	void finishUp(SharedSessionContractImplementor session);

	void setFetchSize(int fetchSize);
}
