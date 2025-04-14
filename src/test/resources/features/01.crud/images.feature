Feature: Read operations on Images via API

  Images can only be read, otherwise people might add inappropriate pictures


  Scenario: Successfully read images
    Given all images are read through the API
    Then the list of images is not empty

