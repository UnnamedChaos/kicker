package de.nieder.kick.common;

import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public abstract class LogParameters {
	/**
	 * The key to be used within {@link MDC} to collect parameters set with
	 * {@link #addParam(Param...)}. This has to be excluded from the
	 * {@link net.logstash.logback.encoder.LogstashEncoder} in logback-spring.xml.
	 */
	static final String PARAMETERS_KEY = "_Parameters";
	/**
	 * The key to be used within {@link MDC} to collect results set with
	 * {@link #addResult(Param...)}. This has to be excluded from the
	 * {@link net.logstash.logback.encoder.LogstashEncoder} in logback-spring.xml.
	 */
	static final String RESULTS_KEY = "_Results";

	/**
	 * Private constructor to prevent subclassing.
	 */
	private LogParameters() {
		// intentionally left blank
	}

	/**
	 * Adds one or more parameters to the final statistics log entry written by the
	 * {@link LoggingFilterHTTP}. Each of the given {@link Param}'s is formatted as
	 * <code>name=value</code>.
	 *
	 * @param params
	 *            the parameter(s) to be added
	 */
	public static void addParam(@NonNull Param... params) {
		add(PARAMETERS_KEY, params);
	}

	/**
	 * A helper used by {@link #addParam(Param...)} and
	 * {@link #addResult(Param...)}. It formats the given <code>params</code> as
	 * comma separated list of <code>name=value</code> entries and appends the
	 * result to the named {@link MDC} value.
	 *
	 * @param mdcKey
	 *            the key of the {@link MDC} value to append the given parameters to
	 * @param params
	 *            the parameter(s) to be added
	 */
	private static void add(@NonNull String mdcKey, @NonNull Param... params) {
		StringBuilder parameters = new StringBuilder();
		String present = MDC.get(mdcKey);
		if (null != present) {
			parameters.append(present).append(", ");
		}
		for (int i = 0; i < params.length; ++i) {
			Param p = params[i];
			if (i > 0) {
				parameters.append(", ");
			}
			parameters.append(p);
		}
		MDC.put(mdcKey, parameters.toString());
	}

	/**
	 * Adds one or more result values to the final statistics log entry written by
	 * the {@link LoggingFilterHTTP}. Each of the given {@link Param}'s is formatted
	 * as <code>name=value</code>.
	 *
	 * @param results
	 *            the result(s) to be added
	 */
	public static void addResult(@NonNull Param... results) {
		add(RESULTS_KEY, results);
	}

	/**
	 * A simple name:value pair.
	 */
	public static final class Param {
		/**
		 * The name. If set to {@code null} the value is formatted without a name.
		 */
		@Nullable
		private final String name;
		/**
		 * The value, maybe {@code null}.
		 */
		@Nullable
		private final Object value;

		/**
		 * Constructor.
		 *
		 * @param name
		 *            the value for {@link #name}
		 * @param value
		 *            the value for {@link #value}
		 */
		public Param(@Nullable final String name, @Nullable final Object value) {
			this.name = name;
			this.value = value;
		}

		/**
		 * @return {@code name=value} in case {@link #name} is not {@code null},
		 *         {@code value} otherwise.
		 */
		@Override
		@NonNull
		public String toString() {
			return (null != this.name ? this.name + '=' : "") + this.value;
		}
	}
}