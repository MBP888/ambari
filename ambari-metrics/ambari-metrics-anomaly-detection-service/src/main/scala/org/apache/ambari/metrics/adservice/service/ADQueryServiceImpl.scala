/**
  * Licensed to the Apache Software Foundation (ASF) under one
  * or more contributor license agreements.  See the NOTICE file
  * distributed with this work for additional information
  * regarding copyright ownership.  The ASF licenses this file
  * to you under the Apache License, Version 2.0 (the
  * "License"); you may not use this file except in compliance
  * with the License.  You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package org.apache.ambari.metrics.adservice.service
import org.apache.ambari.metrics.adservice.db.AdAnomalyStoreAccessor
import org.apache.ambari.metrics.adservice.model.AnomalyType.AnomalyType
import org.apache.ambari.metrics.adservice.model.MetricAnomalyInstance
import org.slf4j.{Logger, LoggerFactory}

import com.google.inject.{Inject, Singleton}

@Singleton
class ADQueryServiceImpl extends ADQueryService {

  val LOG : Logger = LoggerFactory.getLogger(classOf[ADQueryServiceImpl])

  @Inject
  var adAnomalyStoreAccessor: AdAnomalyStoreAccessor = _

  /**
    * Initialize Service
    */
  override def initialize(): Unit = {
    LOG.info("Initializing AD Query Service...")
    adAnomalyStoreAccessor.initialize()
    LOG.info("Successfully initialized AD Query Service.")
  }

  /**
    * Implementation to return list of anomalies satisfying a set of conditions from the anomaly store.
    *
    * @param anomalyType Type of the anomaly (Point In Time / Trend)
    * @param startTime   Start of time range
    * @param endTime     End of time range
    * @param limit       Maximim number of anomaly metrics that need to be returned based on anomaly score.
    * @return
    */
  override def getTopNAnomaliesByType(anomalyType: AnomalyType, startTime: Long, endTime: Long, limit: Int): List[MetricAnomalyInstance] = {
    val anomalies = adAnomalyStoreAccessor.getMetricAnomalies(anomalyType, startTime, endTime, limit)
    anomalies
  }
}