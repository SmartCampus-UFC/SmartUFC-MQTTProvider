#!/bin/bash
#
#  curl commands to reload the data from the previous tutorial
#
#

set -e

printf "Provisioning IoT device groups"


#
# Create a service groups for all UltraLight IoT devices
#

curl -s -X POST \
  "http://$IOTA_HOST:$IOTA_NORTH_PORT/iot/services" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
 "services": [
   {
     "apikey":"scggokgpepnvsb2uv4s40d59o1",
     "cbroker":"'"http://$ORION_HOST:$ORION_PORT"'",
     "entity_type":"UHTSensor",
     "timezone":"America/Fortaleza",
     "description":"Group of UHT sensors",
     "resource":"/",
     "attributes": [
          { "object_id": "t", "name": "temperature", "type": "Float"},
          { "object_id": "h", "name": "humidity", "type": "Float"}
     ],
     "static_attributes": [
          {"name": "category", "type":"Text", "value": ["sensor"]},
          {"name": "controlledProperty", "type": "Text", "value": "temperature"},
          {"name": "function", "type": "Text", "value":["sensing"]},
          {"name": "supportedProtocol", "type": "Text", "value": ["ul20","mqtt"]}
     ]
   },
   {
     "apikey":"scggokgpepnvsb2uv4s40d59o2",
     "cbroker":"'"http://$ORION_HOST:$ORION_PORT"'",
     "entity_type":"AirConditioner",
     "timezone":"America/Fortaleza",
     "description":"Group of IR transmitters for remote Air Conditioner control",
     "resource":"/",
     "attributes":[
       { "object_id": "s", "name": "state", "type": "Text"}
     ],
     "commands": [
        { "name": "on", "type": "command" },
        { "name": "off", "type": "command" }
     ],
     "static_attributes": [
          {"name": "category", "type":"Text", "value": ["actuator"]},
          {"name": "controlledProperty", "type": "Text", "value": "state"},
          {"name": "function", "type": "Text", "value":["onOff"]},
          {"name": "supportedProtocol", "type": "Text", "value": ["ul20","mqtt"]}
     ]
   },
   {
     "apikey":"scggokgpepnvsb2uv4s40d59o3",
     "cbroker":"'"http://$ORION_HOST:$ORION_PORT"'",
     "entity_type":"Lamp",
     "timezone":"America/Fortaleza",
     "description":"Group of smarts switches",
     "resource":"/",
     "attributes":[
       { "object_id": "s", "name": "state", "type": "Text"}
     ],
     "commands": [
        { "name": "on", "type": "command" },
        { "name": "off", "type": "command" }
     ],
     "static_attributes": [
          {"name": "category", "type":"Text", "value": ["actuator"]},
          {"name": "controlledProperty", "type": "Text", "value": "state"},
          {"name": "function", "type": "Text", "value":["onOff"]},
          {"name": "supportedProtocol", "type": "Text", "value": ["ul20","mqtt"]}
     ]
   },
   {
     "apikey":"scggokgpepnvsb2uv4s40d59o4",
     "cbroker":"'"http://$ORION_HOST:$ORION_PORT"'",
     "entity_type":"DoorLocker",
     "timezone":"America/Fortaleza",
     "description":"Group of lockers",
     "resource":"/",
     "commands": [
        {"name": "unlock","type": "command"},
        {"name": "lock","type": "command"}
      ],
     "attributes": [
        {"object_id": "s", "name": "state", "type":"Text"}
      ],
     "static_attributes": [
          {"name": "category", "type":"Text", "value": ["actuator"]},
          {"name": "controlledProperty", "type": "Text", "value": "state"},
          {"name": "function", "type": "Text", "value":["openClose"]},
          {"name": "supportedProtocol", "type": "Text", "value": ["ul20","mqtt"]}
      ]
   },
   {
     "apikey":"scggokgpepnvsb2uv4s40d59o5",
     "cbroker":"'"http://$ORION_HOST:$ORION_PORT"'",
     "entity_type":"NFCReader",
     "timezone":"America/Fortaleza",
     "description":"Group of NFC card readers",
     "resource":"/",
     "attributes": [
          { "object_id": "c", "name": "code", "type": "Text"}
     ],
     "static_attributes": [
          {"name": "category", "type":"Text", "value": ["sensor"]},
          {"name": "controlledProperty", "type": "Text", "value": "nfc_code"},
          {"name": "function", "type": "Text", "value":["sensing"]},
          {"name": "supportedProtocol", "type": "Text", "value": ["ul20","mqtt"]}
     ]
   },
   {
     "apikey":"scggokgpepnvsb2uv4s40d59o6",
     "cbroker":"'"http://$ORION_HOST:$ORION_PORT"'",
     "entity_type":"CurrentSensor",
     "timezone":"America/Fortaleza",
     "description":"Group of Current sensors",
     "resource":"/",
     "attributes": [
          { "object_id": "c", "name": "consumption", "type": "Float"}
     ],
     "static_attributes": [
          {"name": "category", "type":"Text", "value": ["sensor"]},
          {"name": "controlledProperty", "type": "Text", "value": "consumption"},
          {"name": "function", "type": "Text", "value":["sensing"]},
          {"name": "supportedProtocol", "type": "Text", "value": ["ul20","mqtt"]}
     ]
   },
   {
        "apikey":"scggokgpepnvsb2uv4s40d59o7",
        "cbroker":"'"http://$ORION_HOST:$ORION_PORT"'",
        "entity_type":"GasDetector",
        "description":"K-300 Portable ammonia gas Detector Digital",
        "timezone":"America/Fortaleza",
        "resource":"/",
        "attributes": [
             { "object_id": "nh3", "name": "nh3", "type": "Float"},
             { "object_id": "o2", "name": "o2", "type": "Float"},
             { "object_id": "co", "name": "co", "type": "Float"},
             { "object_id": "co2", "name": "co2", "type": "Float"}
        ],
        "static_attributes": [
             {"name": "category", "type":"Text", "value": ["sensor"]},
             {"name": "controlledProperty", "type": "Text", "value": "airPollution"},
             {"name": "function", "type": "Text", "value":["sensing"]},
             {"name": "supportedProtocol", "type": "Text", "value": ["ul20","mqtt"]}
        ]
   },
   {
        "apikey":"scggokgpepnvsb2uv4s40d59o8",
        "cbroker":"'"http://$ORION_HOST:$ORION_PORT"'",
        "entity_type":"WifiSensor",
        "description":"Group of Wifi AP Sensors",
        "timezone":"America/Fortaleza",
        "resource":"/",
        "attributes": [
             { "object_id": "w", "name": "personid", "type": "Float"}
        ],
        "static_attributes": [
             {"name": "category", "type":"Text", "value": ["sensor"]},
             {"name": "controlledProperty", "type": "Text", "value": "personId"},
             {"name": "function", "type": "Text", "value":["sensing"]},
             {"name": "supportedProtocol", "type": "Text", "value": ["ul20","mqtt"]}
        ]
   },
  {
     "apikey":"scggokgpepnvsb2uv4s40d59o9",
     "cbroker":"'"http://$ORION_HOST:$ORION_PORT"'",
     "entity_type":"Vehicle",
     "description":"Vehicle description",
     "timezone":"America/Fortaleza",
     "resource":"/",
     "attributes": [
        {
            "name": "location",
            "type": "geo:point",
            "expression":"${@lat}, ${@long}"
        }
     ],
     "static_attributes": [
          {"name": "category", "type":"Text", "value": ["sensor"]},
          {"name": "controlledProperty", "type": "Text", "value": "geoLocation"},
          {"name": "function", "type": "Text", "value":["sensing"]},
          {"name": "supportedProtocol", "type": "Text", "value": ["ul20","mqtt"]}
     ]
  },
  {
     "apikey":"scggokgpepnvsb2uv4s40d5910",
     "cbroker":"'"http://$ORION_HOST:$ORION_PORT"'",
     "entity_type":"DoorSensor",
     "description":"Group of door contact sensors",
     "timezone":"America/Fortaleza",
     "resource":"/",
     "attributes": [
          { "object_id": "d", "name": "doorcontact", "type": "Text"}
     ],
     "static_attributes": [
          {"name": "category", "type":"Text", "value": ["sensor"]},
          {"name": "controlledProperty", "type": "Text", "value": "doorContact"},
          {"name": "function", "type": "Text", "value":["sensing"]},
          {"name": "supportedProtocol", "type": "Text", "value": ["ul20","mqtt"]}
     ]
  }
 ]
}'

printf "Provisioning device groups subscription"

curl -s -o /dev/null -X POST \
  "http://$ORION_HOST:$ORION_PORT/v2/subscriptions/" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
  "description": "Notify QuantumLeap of temperature and humidity changes of any UHT Sensor",
  "subject": {
    "entities": [
      {
        "idPattern": "UHTSensor.*"
      }
    ],
    "condition": {
      "attrs": [
        "temperature","humidity"
      ]
    }
  },
  "notification": {
    "http": {
      "url": "'"http://$QUANTUMLEAP_HOST:$QUANTUMLEAP_PORT/v2/notify"'"
    },
    "attrs": [
      "temperature","humidity"
    ],
    "metadata": ["dateCreated", "dateModified"]
  },
  "throttling": 1
}'

curl -s -o /dev/null -X POST \
  "http://$ORION_HOST:$ORION_PORT/v2/subscriptions/" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
  "description": "Notify QuantumLeap of state changes of any arconditioner actuator",
  "subject": {
    "entities": [
      {
        "idPattern": "AirConditioner.*"
      }
    ],
    "condition": {
      "attrs": [
        "state"
      ]
    }
  },
  "notification": {
    "http": {
      "url": "'"http://$QUANTUMLEAP_HOST:$QUANTUMLEAP_PORT/v2/notify"'"
    },
    "attrs": [
      "state"
    ],
    "metadata": ["dateCreated", "dateModified"]
  },
  "throttling": 1
}'

curl -s -o /dev/null -X POST \
  "http://$ORION_HOST:$ORION_PORT/v2/subscriptions/" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
  "description": "Notify QuantumLeap of state changes of any lamp actuator",
  "subject": {
    "entities": [
      {
        "idPattern": "Lamp.*"
      }
    ],
    "condition": {
      "attrs": [
        "state"
      ]
    }
  },
  "notification": {
    "http": {
      "url": "'"http://$QUANTUMLEAP_HOST:$QUANTUMLEAP_PORT/v2/notify"'"
    },
    "attrs": [
      "state"
    ],
    "metadata": ["dateCreated", "dateModified"]
  },
  "throttling": 1
}'

curl -s -o /dev/null -X POST \
  "http://$ORION_HOST:$ORION_PORT/v2/subscriptions/" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
  "description": "Notify QuantumLeap of state changes of any door actuator",
  "subject": {
    "entities": [
      {
        "idPattern": "DoorLocker.*"
      }
    ],
    "condition": {
      "attrs": [
        "state","location","room","building"
      ]
    }
  },
  "notification": {
    "http": {
      "url": "'"http://$QUANTUMLEAP_HOST:$QUANTUMLEAP_PORT/v2/notify"'"
    },
    "attrs": [
      "state","location","room","building"
    ],
    "metadata": ["dateCreated", "dateModified"]
  },
  "throttling": 1
}'

curl -s -o /dev/null -X POST \
  "http://$ORION_HOST:$ORION_PORT/v2/subscriptions/" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
  "description": "Notify QuantumLeap of state changes of any NFC readers",
  "subject": {
    "entities": [
      {
        "idPattern": "NFCReader.*"
      }
    ],
    "condition": {
      "attrs": [
        "code"
      ]
    }
  },
  "notification": {
    "http": {
      "url": "'"http://$QUANTUMLEAP_HOST:$QUANTUMLEAP_PORT/v2/notify"'"
    },
    "attrs": [
      "code"
    ],
    "metadata": ["dateCreated", "dateModified"]
  },
  "throttling": 1
}'

curl -s -o /dev/null -X POST \
  "http://$ORION_HOST:$ORION_PORT/v2/subscriptions/" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
  "description": "Notify QuantumLeap of state changes of any current sensor",
  "subject": {
    "entities": [
      {
        "idPattern": "CurrentSensor.*"
      }
    ],
    "condition": {
      "attrs": [
        "consumption"
      ]
    }
  },
  "notification": {
    "http": {
      "url": "'"http://$QUANTUMLEAP_HOST:$QUANTUMLEAP_PORT/v2/notify"'"
    },
    "attrs": [
      "consumption"
    ],
    "metadata": ["dateCreated", "dateModified"]
  },
  "throttling": 1
}'

curl -s -o /dev/null -X POST \
  "http://$ORION_HOST:$ORION_PORT/v2/subscriptions/" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
  "description": "Notify QuantumLeap of gas level changes of any K-300 Sensor",
  "subject": {
    "entities": [
      {
        "idPattern": "GasDetector.*"
      }
    ],
    "condition": {
      "attrs": [
        "nh3","o2","co","co2"
      ]
    }
  },
  "notification": {
    "http": {
      "url": "'"http://$QUANTUMLEAP_HOST:$QUANTUMLEAP_PORT/v2/notify"'"
    },
    "attrs": [
      "nh3","o2","co","co2"
    ],
    "metadata": ["dateCreated", "dateModified"]
  },
  "throttling": 1
}'

curl -s -o /dev/null -X POST \
  "http://$ORION_HOST:$ORION_PORT/v2/subscriptions/" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
  "description": "Notify QuantumLeap of state changes of any WiFi device sensor",
  "subject": {
    "entities": [
      {
        "idPattern": "WifiSensor.*"
      }
    ],
    "condition": {
      "attrs": [
        "personid"
      ]
    }
  },
  "notification": {
    "http": {
      "url": "'"http://$QUANTUMLEAP_HOST:$QUANTUMLEAP_PORT/v2/notify"'"
    },
    "attrs": [
      "personid"
    ],
    "metadata": ["dateCreated", "dateModified"]
  },
  "throttling": 1
}'

curl -s -o /dev/null -X POST \
  "http://$ORION_HOST:$ORION_PORT/v2/subscriptions/" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
   "description": "Notify QuantumLeap of state changes of a vehicle",
    "subject": {
      "entities": [
        {
          "idPattern": "Vehicle.*"
        }
      ],
      "condition": {
        "attrs": [
          "lat","long"
        ]
      }
    },
    "notification": {
      "http": {
        "url": "'"http://$QUANTUMLEAP_HOST:$QUANTUMLEAP_PORT/v2/notify"'"
      },
      "attrs": [
        "lat","long"
      ],
      "metadata": ["dateCreated", "dateModified"]
    },
    "throttling": 1
}'

curl -s -o /dev/null -X POST \
  "http://$ORION_HOST:$ORION_PORT/v2/subscriptions/" \
  -H "Content-Type: application/json" \
  -H "fiware-service: $FIWARE_SERVICE" \
  -H "fiware-servicepath: $FIWARE_SERVICEPATH" \
  -d '{
  "description": "Notify QuantumLeap of state changes of any door sensor",
  "subject": {
    "entities": [
      {
        "idPattern": "DoorSensor.*"
      }
    ],
    "condition": {
      "attrs": [
        "doorcontact"
      ]
    }
  },
  "notification": {
    "http": {
      "url": "'"http://$QUANTUMLEAP_HOST:$QUANTUMLEAP_PORT/v2/notify"'"
    },
    "attrs": [
      "doorcontact"
    ],
    "metadata": ["dateCreated", "dateModified"]
  },
  "throttling": 1
}'

echo -e " \033[1;32mdone\033[0m"