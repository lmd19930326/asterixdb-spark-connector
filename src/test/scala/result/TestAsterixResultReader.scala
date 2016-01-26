/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import org.apache.asterix.connector.Handle
import org.apache.asterix.connector.result.{ResultUtils, AsterixResultReader}
import org.junit.Test

class TestAsterixResultReader extends TestFramework{


  def executeAsync(): Handle ={

    api.executeAsync(
      """
        |use dataverse twitterDataverse;
        |for $x in dataset twitter
        |//order by $x.id
        |return $x
        |
      """.stripMargin)
  }

  @Test
  def testRead() = {
    val handle = executeAsync()
    val locations = api.getResultLocations(handle).locations
    val resultUtils = new ResultUtils
    var locationId :Int = 0
    locations.foreach{location =>
      val resultReader = new AsterixResultReader(location,locationId, handle)
      resultUtils.displayResults(resultReader)
      locationId+=1

    }

  }


}