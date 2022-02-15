@AAT
Feature: Master product update state is sent to read store

  Scenario: Master product update is sent to read store
    Given streams application is running
    And master product update from the file "testData/avro/SourceMasterProduct1100552457.json"
    When given master product update is produced
    Then converted update "testData/readstore/MasterProduct1100552457.json" can be consumed from readstore