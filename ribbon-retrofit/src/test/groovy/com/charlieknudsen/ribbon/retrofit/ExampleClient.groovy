package com.charlieknudsen.ribbon.retrofit

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.client.ClientFactory
import com.netflix.config.ConfigurationManager
import com.netflix.loadbalancer.ZoneAwareLoadBalancer
import retrofit.RestAdapter
import retrofit.converter.JacksonConverter

class ExampleClient {

    public static void main(String[] args) throws Exception {

        ConfigurationManager.loadPropertiesFromResources("github-client.properties");
        LoadBalancedClient githubClient = (LoadBalancedClient) ClientFactory.getNamedClient("github-client");

        // Setup retrofit
        RibbonClient client = new RibbonClient(githubClient)

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer("https://example.com")
                .setConverter(new JacksonConverter(objectMapper))
                .setClient(client)
                .build()

        GitHubService service = restAdapter.create(GitHubService.class);
        List<Repo> repos = service.listRepos("octocat");
        repos.each { it ->
            println it.name
        }

        ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer) githubClient.getLoadBalancer();
        System.out.println(lb.getLoadBalancerStats());
    }

}
