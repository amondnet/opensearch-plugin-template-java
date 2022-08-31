/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.path.to.plugin

import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope
import org.apache.http.util.EntityUtils
import org.hamcrest.Matchers
import org.opensearch.client.Request
import org.opensearch.plugins.Plugin
import org.opensearch.test.OpenSearchIntegTestCase
import org.opensearch.test.OpenSearchIntegTestCase.ClusterScope
import java.io.IOException

@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
@ClusterScope(scope = OpenSearchIntegTestCase.Scope.SUITE)
class RenamePluginIT : OpenSearchIntegTestCase() {
  override fun nodePlugins(): Collection<Class<out Plugin>> {
    return listOf(RenamePlugin::class.java)
  }

  @Throws(IOException::class) fun testPluginInstalled() {
    val response = createRestClient().performRequest(Request("GET", "/_cat/plugins"))
    val body = EntityUtils.toString(response.entity)
    logger.info("response body: {}", body)
    org.hamcrest.MatcherAssert.assertThat(body, Matchers.containsString("rename"))
  }
}