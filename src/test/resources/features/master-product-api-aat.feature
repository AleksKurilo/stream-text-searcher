@AAT
Feature: Master product search GraphQL query

  Background: Readstore contains product data
    Given streams application is running
    And the following products are stored in readstore
    | testData/readstore/MasterProduct1100552457.json |

  Scenario: Master product can be retrieved by gpid
    When client requests master product by gpid "1100552457"
    Then service returns the following master product "testData/graphql/MasterProduct1100552457.json"

  Scenario: Master product can retrieved by sku
    When client requests master product by sku "293678"
    Then service returns the following master product "testData/graphql/MasterProduct1100552457.json"

  Scenario: Master products can be retrieved by name search
    When client searches master products with following params
      | term     | Halcyon |
      | first    | 1       |
      | after    |         |
    Then service returns the following search response "testData/graphql/MasterProductConnection.json"

  Scenario: Master products can be retrieved via federation query
    When client requests master product by gpid "1100552457" via federation query
    Then service returns the following federated response "testData/graphql/MasterProduct1100552457.json"