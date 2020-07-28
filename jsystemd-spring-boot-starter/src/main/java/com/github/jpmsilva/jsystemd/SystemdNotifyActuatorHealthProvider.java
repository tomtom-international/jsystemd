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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;

/**
 * Implementation of {@link HealthProvider} that provides application health based on Spring Boot Actuator Health Indicators.
 *
 * @author Christian Lorenz
 */
public class SystemdNotifyActuatorHealthProvider implements SystemdNotifyStatusProvider, HealthProvider {

  private static final Logger LOG = LoggerFactory.getLogger(SystemdNotifyActuatorHealthProvider.class);

  private final List<HealthIndicator> healthIndicators;
  private final Set<Status> unhealthyStatusCodes;

  /**
   * @param healthIndicators     Spring Boot Actuator Health Indicators
   * @param unhealthyStatusCodes list of status codes considered as unhealthy
   */
  public SystemdNotifyActuatorHealthProvider(List<HealthIndicator> healthIndicators, Set<Status> unhealthyStatusCodes) {
    this.healthIndicators = healthIndicators;
    this.unhealthyStatusCodes = unhealthyStatusCodes;
    if (this.unhealthyStatusCodes.isEmpty()) {
      LOG.warn("no status codes considered as unhealthy");
    } else {
      LOG.debug("status codes considered as unhealthy={}", unhealthyStatusCodes);
    }
  }

  @Override
  public Health health() {
    Collection<HealthIndicator> unhealthyIndicators = healthIndicators
        .stream()
        .filter(it -> unhealthyStatusCodes.contains(it.health().getStatus())).collect(Collectors.toList());
    LOG.debug("application health state={}", unhealthyIndicators.stream().map(HealthIndicator::health).collect(Collectors.toList()));
    boolean healthy = unhealthyIndicators.isEmpty();
    return new Health(healthy, unhealthyIndicators.stream()
        .map(it -> it.health().getDetails().entrySet())
        .flatMap(Collection::stream)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
  }

  @Override
  public String status() {
    Health health = health();
    return "health status: " + (health.healthy ? "healthy" : "unhealthy=" + health);
  }
}
