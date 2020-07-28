/*
 * Copyright 2020 TomTom N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jpmsilva.jsystemd;

import java.util.Collections;
import java.util.Map;

/**
 * A health provider can be used to control the watchdog heartbeat. An unhealthy state does not trigger the heartbeat.
 *
 * @author Christian Lorenz
 */
public interface HealthProvider {

  /**
   * @return true if application is healthy, otherwise false
   */
  Health health();

  /**
   * Wrapper of health state.
   */
  class Health {
    /** true if healthy, otherwise false */
    public final boolean healthy;
    /** immutable */
    public final Map<String, Object> details;

    /**
     * @throws IllegalArgumentException if details is null
     */
    public Health(boolean healthy, Map<String, Object> details) {
      if (details == null) {
          throw new IllegalArgumentException("details must not be null");
      }
      this.healthy = healthy;
      this.details = Collections.unmodifiableMap(details);
    }

    @Override
    public String toString() {
      return "Health{" + "healthy=" + healthy + ", details=" + details + '}';
    }

    public static Health healthy() {
        return new Health(true, Collections.emptyMap());
    }

    public static Health unhealthy(Map<String, Object> details) {
        return new Health(false, details);
    }
  }
}
