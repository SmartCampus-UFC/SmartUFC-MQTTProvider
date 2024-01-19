--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2 (Debian 15.2-1.pgdg110+1)
-- Dumped by pg_dump version 15.2

-- Started on 2024-01-18 17:16:53

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 16385)
-- Name: actuator; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actuator (
    id bigint NOT NULL,
    version integer NOT NULL,
    default_state character varying(255) NOT NULL,
    description character varying(255),
    name character varying(255),
    object_id character varying(255),
    periodicity integer NOT NULL,
    smart_spec_space_id bigint
);


ALTER TABLE public.actuator OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16390)
-- Name: actuator_commands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actuator_commands (
    actuator_id bigint NOT NULL,
    commands_id bigint NOT NULL
);


ALTER TABLE public.actuator_commands OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16393)
-- Name: command; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.command (
    id bigint NOT NULL,
    version integer NOT NULL,
    name character varying(255),
    state character varying(255)
);


ALTER TABLE public.command OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16398)
-- Name: dataset_file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dataset_file (
    id bigint NOT NULL,
    version integer NOT NULL,
    content_length bigint NOT NULL,
    description character varying(255),
    file_name character varying(255),
    mime_type character varying(255),
    smart_spec_dataset_id bigint NOT NULL
);


ALTER TABLE public.dataset_file OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16403)
-- Name: device; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device (
    id bigint NOT NULL,
    version integer NOT NULL,
    description character varying(255),
    instances integer DEFAULT 1 NOT NULL,
    device_id character varying(255),
    device_group_id bigint NOT NULL
);


ALTER TABLE public.device OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16409)
-- Name: device_actuators; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device_actuators (
    device_id bigint NOT NULL,
    actuators_id bigint NOT NULL
);


ALTER TABLE public.device_actuators OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16412)
-- Name: device_ed_sensors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device_ed_sensors (
    device_id bigint NOT NULL,
    ed_sensors_id bigint NOT NULL
);


ALTER TABLE public.device_ed_sensors OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16415)
-- Name: device_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device_group (
    id bigint NOT NULL,
    version integer NOT NULL,
    api_key character varying(255),
    description character varying(255),
    name character varying(255)
);


ALTER TABLE public.device_group OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16420)
-- Name: device_mobile_sensors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device_mobile_sensors (
    device_id bigint NOT NULL,
    mobile_sensors_id bigint NOT NULL
);


ALTER TABLE public.device_mobile_sensors OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16423)
-- Name: device_td_sensors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device_td_sensors (
    device_id bigint NOT NULL,
    td_sensors_id bigint NOT NULL
);


ALTER TABLE public.device_td_sensors OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16426)
-- Name: edsensor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.edsensor (
    id bigint NOT NULL,
    version integer NOT NULL,
    description character varying(255),
    name character varying(255),
    object_id character varying(255),
    smart_spec_sensor_id bigint NOT NULL
);


ALTER TABLE public.edsensor OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16431)
-- Name: generator; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.generator
    START WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.generator OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16432)
-- Name: mobile_sensor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobile_sensor (
    id bigint NOT NULL,
    version integer NOT NULL,
    description character varying(255),
    mobile_id character varying(255),
    name character varying(255),
    object_id character varying(255),
    periodicity integer NOT NULL,
    sumo_trace_id bigint NOT NULL
);


ALTER TABLE public.mobile_sensor OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16437)
-- Name: scenario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scenario (
    id bigint NOT NULL,
    version integer NOT NULL,
    description character varying(255),
    name character varying(255)
);


ALTER TABLE public.scenario OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16442)
-- Name: scenario_devices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scenario_devices (
    scenario_id bigint NOT NULL,
    devices_id bigint NOT NULL
);


ALTER TABLE public.scenario_devices OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16445)
-- Name: smart_spec_dataset; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.smart_spec_dataset (
    id bigint NOT NULL,
    version integer NOT NULL,
    code_path character varying(255),
    description character varying(255),
    name character varying(255),
    status character varying(255),
    base_start_date character varying(255)
);


ALTER TABLE public.smart_spec_dataset OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16450)
-- Name: smart_spec_dataset_file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.smart_spec_dataset_file (
    id bigint NOT NULL,
    version integer NOT NULL,
    content_length bigint NOT NULL,
    description character varying(255),
    file_name character varying(255),
    mime_type character varying(255),
    smart_spec_dataset_id bigint NOT NULL
);


ALTER TABLE public.smart_spec_dataset_file OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16455)
-- Name: smart_spec_meta_sensor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.smart_spec_meta_sensor (
    id bigint NOT NULL,
    version integer NOT NULL,
    description character varying(255),
    smart_spec_dataset_id bigint NOT NULL,
    json_id bigint NOT NULL
);


ALTER TABLE public.smart_spec_meta_sensor OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 16458)
-- Name: smart_spec_sensor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.smart_spec_sensor (
    id bigint NOT NULL,
    version integer NOT NULL,
    description character varying(255),
    smart_spec_dataset_id bigint NOT NULL,
    json_id bigint NOT NULL,
    json_meta_sensor_id bigint NOT NULL,
    file_name character varying(255)
);


ALTER TABLE public.smart_spec_sensor OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16463)
-- Name: smart_spec_space; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.smart_spec_space (
    id bigint NOT NULL,
    version integer NOT NULL,
    description character varying(255),
    smart_spec_dataset_id bigint NOT NULL,
    json_id bigint NOT NULL,
    file_name character varying(255)
);


ALTER TABLE public.smart_spec_space OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 16468)
-- Name: sumo_trace; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sumo_trace (
    id bigint NOT NULL,
    version integer NOT NULL,
    content_length bigint NOT NULL,
    file_name character varying(255),
    mime_type character varying(255),
    absolute_path character varying(255),
    description character varying(255),
    canonical_path character varying(255),
    code_path character varying(255)
);


ALTER TABLE public.sumo_trace OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16473)
-- Name: tdsensor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tdsensor (
    id bigint NOT NULL,
    version integer NOT NULL,
    data_type smallint NOT NULL,
    description character varying(255),
    max character varying(255) NOT NULL,
    min character varying(255) NOT NULL,
    name character varying(255),
    object_id character varying(255),
    periodicity integer NOT NULL,
    CONSTRAINT tdsensor_data_type_check CHECK (((data_type >= 0) AND (data_type <= 3)))
);


ALTER TABLE public.tdsensor OWNER TO postgres;

--
-- TOC entry 3463 (class 0 OID 16385)
-- Dependencies: 214
-- Data for Name: actuator; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.actuator (id, version, default_state, description, name, object_id, periodicity, smart_spec_space_id) FROM stdin;
1101	2	ON	Lamp mode on	lampStartsOn	s	15	\N
1103	2	OFF	Lamp mode off	lampStartsOff	s	15	\N
1107	2	LOCKED	Door that starts locked	doorStartsLocked	s	45	\N
1108	2	UNLOCKED	Door that starts unlocked	doorStartsUnlocked	s	45	\N
1106	3	OFF	Air Conditioner mode off	airConditionerStartsOff	s	30	\N
1105	2	ON	Air Conditioner mode on	airConditionerStartsOn	s	30	\N
9851	1	OFF	Template of lamp device in main-lobby1	edActTemp001	s	10	9404
\.


--
-- TOC entry 3464 (class 0 OID 16390)
-- Dependencies: 215
-- Data for Name: actuator_commands; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.actuator_commands (actuator_id, commands_id) FROM stdin;
1101	1001
1101	1000
1103	1001
1103	1000
1105	1001
1105	1000
1106	1001
1106	1000
1107	1002
1107	1003
1108	1002
1108	1003
9851	1001
9851	1000
\.


--
-- TOC entry 3465 (class 0 OID 16393)
-- Dependencies: 216
-- Data for Name: command; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.command (id, version, name, state) FROM stdin;
1000	0	on	ON
1001	0	off	OFF
1002	0	lock	LOCKED
1003	0	unlock	UNLOCKED
\.


--
-- TOC entry 3466 (class 0 OID 16398)
-- Dependencies: 217
-- Data for Name: dataset_file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dataset_file (id, version, content_length, description, file_name, mime_type, smart_spec_dataset_id) FROM stdin;
\.


--
-- TOC entry 3467 (class 0 OID 16403)
-- Dependencies: 218
-- Data for Name: device; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device (id, version, description, instances, device_id, device_group_id) FROM stdin;
1305	0	Lamp 001 001 001	1	lamp001001001	1053
1306	0	Lamp 001 001 002	1	lamp001001002	1053
1307	0	Air Conditioner 001 001 001	1	airConditioner001001001	1052
1308	0	Air Conditioner 001 002 001	1	airConditioner001002001	1052
1309	0	Door 001 001 001	1	door001001001	1054
1310	0	Door 001 002 001	1	door001002001	1054
9802	0	ED Wifi Sensor 001	1	edWifiSensor001	9001
9601	2	ED Temperature Sensor 001	1	edTempSensor001	1051
9801	1	ED Temperature Sensor 002	1	edTempSensor002	1051
9803	0	ED Wifi Sensor 002	1	edWifiSensor002	9001
9804	0	ED Door Sensor 001	1	edDoorSensor001	9751
9805	0	ED Door Sensor 002	1	edDoorSensor002	9751
9901	0	ED Lamp Actuator main lobby 1	1	edLampActuator001	1053
1303	1	UHT Sensor 001 001 001	1	uhtSensor001001001	1051
1304	2	UHT Sensor 001 002 001	1	uhtSensor001002001	1051
10151	0	10 Random UHT Sensors	10	mult10UHTSensor	1051
10152	0	25 Random UHT Sensors	25	mult25UHTSensor	1051
8151	1	Vehicle 010101	1	vehicle010101	1058
10153	0	50 Random UHT Sensors	50	mult50UHTSensor	1051
10154	0	75 Random UHT Sensors	75	mult75UHTSensor	1051
10155	0	100 Random UHT Sensors	100	mult100UHTSensor	1051
10156	0	10 Air Conditioners Actuators	10	mult10AirConditioner	1052
10157	0	25 Air Conditioners Actuators	25	mult25AirConditioner	1052
10158	0	50 Air Conditioners Actuators	50	mult50AirConditioner	1052
10159	0	75 Air Conditioners Actuators	75	mult75AirConditioner	1052
10160	0	100 Air Conditioners Actuators	100	mult100AirConditioner	1052
10161	0	10 Lamp Actuators	10	mult10Lamp	1053
10162	0	25 Lamp Actuators	25	mult25Lamp	1053
10163	0	50 Lamp Actuators	50	mult50Lamp	1053
10164	0	75 Lamp Actuators	75	mult75Lamp	1053
10165	0	100 Lamp Actuators	100	mult100Lamp	1053
10166	0	10 Door Locker Actuators	10	mult10DoorLocker	1054
10167	0	25 Door Locker Actuators	25	mult25DoorLocker	1054
10168	0	50 Door Locker Actuators	50	mult50DoorLocker	1054
10169	0	75 Door Locker Actuators	75	mult75DoorLocker	1054
10170	0	100 Door Locker Actuators	100	mult100DoorLocker	1054
10171	0	10 NFC Sensors	10	mult10NFCSensor	1055
10172	0	25 NFC Sensors	25	mult25NFCSensor	1055
10173	0	50 NFC Sensors	50	mult50NFCSensor	1055
10174	0	75 NFC Sensors	75	mult75NFCSensor	1055
10175	0	100 NFC Sensors	100	mult100NFCSensor	1055
10176	0	10 Current Sensors	10	mult10CurrSensor	1056
10177	0	25 Current Sensors	25	mult25CurrSensor	1056
10178	0	50 Current Sensors	50	mult50CurrSensor	1056
10179	0	75 Current Sensors	75	mult75CurrSensor	1056
10180	0	100 Current Sensors	100	mult100CurrSensor	1056
10181	0	10 Gas Sensors	10	mult10GasSensor	1057
10182	0	25 Gas Sensors	25	mult25GasSensor	1057
10183	1	50 Gas Sensors	50	mult50GasSensor	1057
10184	0	75 Gas Sensors	75	mult75GasSensor	1057
10185	0	100 Gas Sensors	100	mult100GasSensor	1057
10186	0	10 WiFi Sensors	10	mult10WiFiSensor	9001
10187	0	25 WiFi Sensors	25	mult25WiFiSensor	9001
10188	0	50 WiFi Sensors	50	mult50WiFiSensor	9001
10189	0	75 WiFi Sensors	75	mult75WiFiSensor	9001
10190	0	100 WiFi Sensors	100	mult100WiFiSensor	9001
10191	0	10 Vehicle Sensors	10	mult10Vehicle	1058
10192	0	25 Vehicle Sensors	25	mult25Vehicle	1058
10193	0	50 Vehicle Sensors	50	mult50Vehicle	1058
10194	0	75 Vehicle Sensors	75	mult75Vehicle	1058
10195	0	100 Vehicle Sensors	100	mult100Vehicle	1058
10196	0	10 Door Contact Sensors	10	mult10DoorSensor	9751
10197	0	25 Door Contact Sensors	25	mult25DoorSensor	9751
10198	0	50 Door Contact Sensors	50	mult50DoorSensor	9751
10199	0	75 Door Contact Sensors	75	mult75DoorSensor	9751
10200	0	100 Door Contact Sensors	100	mult100DoorSensor	9751
10202	1	200 Random UHT Sensors	200	mult200UHTSensor	1051
10203	1	250 Random UHT Sensors	250	mult250UHTSensor	1051
10204	1	300 Random UHT Sensors	300	mult300UHTSensor	1051
10205	1	150 Air Conditioners Actuators	150	mult150AirConditioner	1052
10207	1	250 Air Conditioners Actuators	250	mult250AirConditioner	1052
10208	1	300 Air Conditioners Actuators	300	mult300AirConditioner	1052
10209	1	150 Lamp Actuators	150	mult150Lamp	1053
10210	1	200 Lamp Actuators	200	mult200Lamp	1053
10211	1	250 Lamp Actuators	250	mult250Lamp	1053
10212	1	300 Lamp Actuators	300	mult300Lamp	1053
10213	1	150 Door Locker Actuators	150	mult150DoorLocker	1054
10214	1	200 Door Locker Actuators	200	mult200DoorLocker	1054
10215	1	250 Door Locker Actuators	250	mult250DoorLocker	1054
10216	1	300 Door Locker Actuators	300	mult300DoorLocker	1054
10217	1	150 NFC Sensors	150	mult150NFCSensor	1055
10218	1	200 NFC Sensors	200	mult200NFCSensor	1055
10219	1	250 NFC Sensors	250	mult250NFCSensor	1055
10220	1	300 NFC Sensors	300	mult300NFCSensor	1055
10201	1	150 Random UHT Sensors	150	mult150UHTSensor	1051
10206	1	200 Air Conditioners Actuators	200	mult200AirConditioner	1052
10221	1	150 Current Sensors	150	mult150CurrSensor	1056
10222	1	200 Current Sensors	200	mult200CurrSensor	1056
10223	1	250 Current Sensors	250	mult250CurrSensor	1056
10224	1	300 Current Sensors	300	mult300CurrSensor	1056
10225	1	150 Gas Sensors	150	mult150GasSensor	1057
10226	1	200 Gas Sensors	200	mult200GasSensor	1057
10227	1	250 Gas Sensors	250	mult250GasSensor	1057
10228	1	300 Gas Sensors	300	mult300GasSensor	1057
10229	1	150 WiFi Sensors	150	mult150WiFiSensor	9001
10230	1	200 WiFi Sensors	200	mult200WiFiSensor	9001
10231	1	250 WiFi Sensors	250	mult250WiFiSensor	9001
10232	1	300 WiFi Sensors	300	mult300WiFiSensor	9001
10233	1	150 Vehicle Sensors	150	mult150Vehicle	1058
10234	1	200 Vehicle Sensors	200	mult200Vehicle	1058
10235	1	250 Vehicle Sensors	250	mult250Vehicle	1058
10236	1	300 Vehicle Sensors	300	mult300Vehicle	1058
10237	1	150 Door Contact Sensors	150	mult150DoorSensor	9751
10238	1	200 Door Contact Sensors	200	mult200DoorSensor	9751
10239	1	250 Door Contact Sensors	250	mult250DoorSensor	9751
10240	1	300 Door Contact Sensors	300	mult300DoorSensor	9751
\.


--
-- TOC entry 3468 (class 0 OID 16409)
-- Dependencies: 219
-- Data for Name: device_actuators; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device_actuators (device_id, actuators_id) FROM stdin;
1305	1103
1306	1101
1307	1105
1308	1106
1309	1107
1310	1108
9901	9851
10156	1106
10157	1106
10158	1106
10159	1106
10160	1106
10161	1101
10162	1101
10163	1101
10164	1101
10165	1101
10166	1107
10167	1107
10168	1107
10169	1107
10170	1107
10205	1106
10206	1106
10207	1106
10208	1106
10209	1101
10210	1101
10211	1101
10212	1101
10213	1107
10214	1107
10215	1107
10216	1107
\.


--
-- TOC entry 3469 (class 0 OID 16412)
-- Dependencies: 220
-- Data for Name: device_ed_sensors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device_ed_sensors (device_id, ed_sensors_id) FROM stdin;
9601	9551
9801	9701
9802	9702
9803	9703
9804	9704
9805	9705
10186	9703
10187	9703
10188	9703
10189	9703
10190	9703
10196	9705
10197	9705
10198	9705
10199	9705
10200	9705
10229	9703
10230	9703
10231	9703
10232	9703
10237	9705
10238	9705
10239	9705
10240	9705
\.


--
-- TOC entry 3470 (class 0 OID 16415)
-- Dependencies: 221
-- Data for Name: device_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device_group (id, version, api_key, description, name) FROM stdin;
1052	0	scggokgpepnvsb2uv4s40d59o2	Group of IR transmitters for remote Air Conditioner control	AirConditioner
1053	0	scggokgpepnvsb2uv4s40d59o3	Group of smarts switches	Lamp
1055	0	scggokgpepnvsb2uv4s40d59o5	Group of NFC card readers	NFCReader
1056	0	scggokgpepnvsb2uv4s40d59o6	Group of Current sensors	CurrentSensor
1058	0	scggokgpepnvsb2uv4s40d59o9	Group of Vehicles	Vehicle
1057	1	scggokgpepnvsb2uv4s40d59o7	Group of K-300 Portable ammonia gas Detector Digital	GasDetector
9001	0	scggokgpepnvsb2uv4s40d59o8	Group of Wifi AP Sensors	WifiSensor
1054	1	scggokgpepnvsb2uv4s40d59o4	Group of lockers	DoorLocker
9751	0	scggokgpepnvsb2uv4s40d5910	Group of door contact sensors	DoorSensor
1051	1	scggokgpepnvsb2uv4s40d59o1	Group of UHT sensors	UHTSensor
\.


--
-- TOC entry 3471 (class 0 OID 16420)
-- Dependencies: 222
-- Data for Name: device_mobile_sensors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device_mobile_sensors (device_id, mobile_sensors_id) FROM stdin;
8151	8101
10191	8101
10192	8101
10193	8101
10194	8101
10195	8101
10233	8101
10234	8101
10235	8101
10236	8101
\.


--
-- TOC entry 3472 (class 0 OID 16423)
-- Dependencies: 223
-- Data for Name: device_td_sensors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device_td_sensors (device_id, td_sensors_id) FROM stdin;
1303	1151
1303	1152
1304	1151
1304	1152
10151	1151
10151	1152
10152	1151
10152	1152
10153	1151
10153	1152
10154	1151
10154	1152
10155	1151
10155	1152
10171	10107
10172	10107
10173	10107
10174	10107
10175	10107
10176	10101
10177	10101
10178	10101
10179	10101
10180	10101
10181	10103
10181	10104
10181	10102
10181	10105
10182	10103
10182	10104
10182	10102
10182	10105
10183	10103
10183	10104
10183	10102
10183	10105
10184	10103
10184	10104
10184	10102
10184	10105
10185	10103
10185	10104
10185	10102
10185	10105
10201	1151
10201	1152
10202	1151
10202	1152
10203	1151
10203	1152
10204	1151
10204	1152
10217	10107
10218	10107
10219	10107
10220	10107
10221	10101
10222	10101
10223	10101
10224	10101
10225	10104
10225	10103
10225	10102
10225	10105
10226	10104
10226	10103
10226	10102
10226	10105
10227	10104
10227	10103
10227	10102
10227	10105
10228	10104
10228	10103
10228	10102
10228	10105
\.


--
-- TOC entry 3473 (class 0 OID 16426)
-- Dependencies: 224
-- Data for Name: edsensor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.edsensor (id, version, description, name, object_id, smart_spec_sensor_id) FROM stdin;
9701	1	Template from temperature sensor from main-lobby1	edtempTemp1101	t	9371
9551	1	Template from temperature sensor 1100	edtempTemp1100	t	9374
9702	2	Template from Wifi sensor from main-lobby1	edwifiTemp1100	w	9351
9703	0	Template Wifi Sensor from main-lobb2	edwifiTemp1101	w	9353
9705	2	Template Door Sensor from 1500	eddoorTemp1101	d	9370
9704	2	Template Door Sensor from 1422	eddoorTemp1100	d	9369
\.


--
-- TOC entry 3475 (class 0 OID 16432)
-- Dependencies: 226
-- Data for Name: mobile_sensor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mobile_sensor (id, version, description, mobile_id, name, object_id, periodicity, sumo_trace_id) FROM stdin;
8101	0	v1	f_0.0	v1	vehicle	2	8051
\.


--
-- TOC entry 3476 (class 0 OID 16437)
-- Dependencies: 227
-- Data for Name: scenario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scenario (id, version, description, name) FROM stdin;
10051	0	Testing all created devices	allinone
10201	0	Testing 10 devices per Device Group (Total 100)	testMult10PerDeviceGroup
10202	0	Testing 25 devices per Device Group (Total 250)	testMult25PerDeviceGroup
10203	0	Testing 50 devices per Device Group (Total 500)	testMult50PerDeviceGroup
10204	0	Testing 75 devices per Device Group (Total 750)	testMult75PerDeviceGroup
10205	0	Testing 100 devices per Device Group (Total 1000)	testMult100PerDeviceGroup
10251	0	Testing 150 devices per Device Group (Total 1500)	testMult150PerDeviceGroup
10252	0	Testing 200 devices per Device Group (Total 2000)	testMult200PerDeviceGroup
10253	0	Testing 250 devices per Device Group (Total 2500)	testMult250PerDeviceGroup
10254	0	Testing 300 devices per Device Group (Total 3000)	testMult300PerDeviceGroup
\.


--
-- TOC entry 3477 (class 0 OID 16442)
-- Dependencies: 228
-- Data for Name: scenario_devices; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scenario_devices (scenario_id, devices_id) FROM stdin;
10051	1310
10051	9802
10051	9901
10051	9801
10051	1303
10051	9805
10051	9601
10051	1308
10051	8151
10051	1306
10051	1305
10051	1309
10051	9804
10051	1304
10051	9803
10051	1307
10201	10151
10201	10186
10201	10166
10201	10196
10201	10161
10201	10176
10201	10156
10201	10181
10201	10171
10201	10191
10202	10197
10202	10182
10202	10167
10202	10177
10202	10172
10202	10157
10202	10192
10202	10187
10202	10162
10202	10152
10203	10178
10203	10158
10203	10198
10203	10173
10203	10193
10203	10183
10203	10153
10203	10188
10203	10168
10203	10163
10204	10179
10204	10154
10204	10184
10204	10169
10204	10159
10204	10189
10204	10164
10204	10194
10204	10174
10204	10199
10205	10185
10205	10160
10205	10190
10205	10155
10205	10170
10205	10165
10205	10200
10205	10195
10205	10175
10205	10180
10251	10233
10251	10217
10251	10209
10251	10225
10251	10213
10251	10205
10251	10221
10251	10229
10251	10237
10251	10201
10252	10238
10252	10202
10252	10234
10252	10230
10252	10222
10252	10206
10252	10210
10252	10226
10252	10218
10252	10214
10253	10227
10253	10235
10253	10203
10253	10239
10253	10219
10253	10231
10253	10211
10253	10223
10253	10215
10253	10207
10254	10232
10254	10228
10254	10220
10254	10204
10254	10224
10254	10216
10254	10212
10254	10240
10254	10236
10254	10208
\.


--
-- TOC entry 3478 (class 0 OID 16445)
-- Dependencies: 229
-- Data for Name: smart_spec_dataset; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.smart_spec_dataset (id, version, code_path, description, name, status, base_start_date) FROM stdin;
2251	1	204f2dc6-6b53-47d4-8dad-e1f9c2817850	Testing Smart Building	Smart Building	NotAvailable	2020-01-06 00:00:00
\.


--
-- TOC entry 3479 (class 0 OID 16450)
-- Dependencies: 230
-- Data for Name: smart_spec_dataset_file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.smart_spec_dataset_file (id, version, content_length, description, file_name, mime_type, smart_spec_dataset_id) FROM stdin;
2301	0	4963		obs_msid_2.csv	text/csv	2251
2302	0	201191		obs_msid_3.csv	text/csv	2251
2303	0	3608071		obs_msid_1.csv	text/csv	2251
2304	0	13597434		data.csv	text/csv	2251
2305	0	218		MetaSensors.json	application/json	2251
2306	0	20661		Spaces.json	application/json	2251
2307	0	6051		Sensors.json	application/json	2251
\.


--
-- TOC entry 3480 (class 0 OID 16455)
-- Dependencies: 231
-- Data for Name: smart_spec_meta_sensor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.smart_spec_meta_sensor (id, version, description, smart_spec_dataset_id, json_id) FROM stdin;
9304	0	WiFi Sensor	2251	1
9305	0	Door Sensor	2251	2
9306	0	Temperature Sensors	2251	3
\.


--
-- TOC entry 3481 (class 0 OID 16458)
-- Dependencies: 232
-- Data for Name: smart_spec_sensor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.smart_spec_sensor (id, version, description, smart_spec_dataset_id, json_id, json_meta_sensor_id, file_name) FROM stdin;
9351	0	main-lobby1 (WiFi AP)	2251	1	1	\N
9352	0	lobby-1427-1429 (WiFi AP)	2251	2	1	\N
9353	0	main-lobb2 (WiFi AP)	2251	3	1	\N
9354	0	1100 (WiFi AP)	2251	4	1	\N
9355	0	1200 (WiFi AP)	2251	5	1	\N
9356	0	1300 (WiFi AP)	2251	6	1	\N
9357	0	1422 (WiFi AP)	2251	7	1	\N
9358	0	1433 (WiFi AP)	2251	8	1	\N
9359	0	1500 (WiFi AP)	2251	9	1	\N
9360	0	2011 (WiFi AP)	2251	10	1	\N
9361	0	2052 (WiFi AP)	2251	11	1	\N
9362	0	2059 (WiFi AP)	2251	12	1	\N
9363	0	2069 (WiFi AP)	2251	13	1	\N
9364	0	2076 (WiFi AP)	2251	14	1	\N
9365	0	2084 (WiFi AP)	2251	15	1	\N
9366	0	12001 (Door)	2251	16	2	\N
9367	0	12002 (Door)	2251	17	2	\N
9368	0	1420 (Door)	2251	18	2	\N
9369	0	1422 (Door)	2251	19	2	\N
9370	0	1500 (Door)	2251	20	2	\N
9371	0	main-lobby1 (Temperature)	2251	21	3	\N
9372	0	lobby-1427-1429 (Temperature)	2251	22	3	\N
9373	0	main-lobb2 (Temperature)	2251	23	3	\N
9374	0	1100 (Temperature)	2251	24	3	\N
9375	0	1200 (Temperature)	2251	25	3	\N
9376	0	1300 (Temperature)	2251	26	3	\N
9377	0	1422 (Temperature)	2251	27	3	\N
9378	0	1433 (Temperature)	2251	28	3	\N
9379	0	1500 (Temperature)	2251	29	3	\N
9380	0	2011 (Temperature)	2251	30	3	\N
9381	0	2052 (Temperature)	2251	31	3	\N
9382	0	2059 (Temperature)	2251	32	3	\N
9383	0	2069 (Temperature)	2251	33	3	\N
9384	0	2076 (Temperature)	2251	34	3	\N
9385	0	2084 (Temperature)	2251	35	3	\N
\.


--
-- TOC entry 3482 (class 0 OID 16463)
-- Dependencies: 233
-- Data for Name: smart_spec_space; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.smart_spec_space (id, version, description, smart_spec_dataset_id, json_id, file_name) FROM stdin;
9401	0	outside	2251	0	\N
9402	0	stairs-floor1	2251	1	\N
9403	0	exit-stairs1	2251	2	\N
9404	0	main-lobby1	2251	3	\N
9405	0	hallway-main-lobby1	2251	4	\N
9406	0	exit-main-lobby1	2251	5	\N
9407	0	hallway-elevator-1a	2251	6	\N
9408	0	hallway-elevator-1b	2251	7	\N
9409	0	elevator1	2251	8	\N
9410	0	restroom1	2251	9	\N
9411	0	hallway-restroom1	2251	10	\N
9412	0	lobby-1427-1429	2251	11	\N
9413	0	janitor-closet1	2251	12	\N
9414	0	stairs2	2251	13	\N
9415	0	hallway-stairs2	2251	14	\N
9416	0	elevator2	2251	15	\N
9417	0	hallway-elevator2a	2251	16	\N
9418	0	hallway-elevator2b	2251	17	\N
9419	0	main-lobby2	2251	18	\N
9420	0	kitchen2	2251	19	\N
9421	0	hallway-kitchen2	2251	20	\N
9422	0	restroom2	2251	21	\N
9423	0	hallway-restroom2	2251	22	\N
9424	0	janitor-closet2	2251	23	\N
9425	0	1100	2251	1100	\N
9426	0	1200	2251	1200	\N
9427	0	1300	2251	1300	\N
9428	0	1420	2251	1420	\N
9429	0	1422	2251	1422	\N
9430	0	1423	2251	1423	\N
9431	0	1425	2251	1425	\N
9432	0	1427	2251	1427	\N
9433	0	1429	2251	1429	\N
9434	0	1431	2251	1431	\N
9435	0	1433	2251	1433	\N
9436	0	1500	2251	1500	\N
9437	0	1600	2251	1600	\N
9438	0	2011	2251	2011	\N
9439	0	2048	2251	2048	\N
9440	0	2051	2251	2051	\N
9441	0	2052	2251	2052	\N
9442	0	2054	2251	2054	\N
9443	0	2056	2251	2056	\N
9444	0	2058	2251	2058	\N
9445	0	2059	2251	2059	\N
9446	0	2061	2251	2061	\N
9447	0	2062	2251	2062	\N
9448	0	2064	2251	2064	\N
9449	0	2065	2251	2065	\N
9450	0	2066	2251	2066	\N
9451	0	2068	2251	2068	\N
9452	0	2069	2251	2069	\N
9453	0	2072	2251	2072	\N
9454	0	2074	2251	2074	\N
9455	0	2076	2251	2076	\N
9456	0	2081	2251	2081	\N
9457	0	2082	2251	2082	\N
9458	0	2084	2251	2084	\N
9459	0	2086	2251	2086	\N
9460	0	2088	2251	2088	\N
9461	0	2089	2251	2089	\N
9462	0	2091	2251	2091	\N
9463	0	2092	2251	2092	\N
9464	0	2099	2251	2099	\N
9465	0	exit-1100a	2251	11001	\N
9466	0	exit-1100b	2251	11002	\N
9467	0	exit-1200a	2251	12001	\N
9468	0	exit-1200b	2251	12002	\N
9469	0	exit-1300a	2251	13001	\N
9470	0	exit-1300b	2251	13002	\N
9471	0	exit-1420	2251	14201	\N
9472	0	exit-1422	2251	14221	\N
9473	0	exit-1423	2251	14231	\N
9474	0	hallway-1423	2251	14232	\N
9475	0	hallway-1425	2251	14251	\N
9476	0	hallway-1427	2251	14271	\N
9477	0	hallway-1429	2251	14291	\N
9478	0	hallway-1431	2251	14311	\N
9479	0	hallway-1500a	2251	15001	\N
9480	0	hallway-1500b	2251	15002	\N
9481	0	exit-1500	2251	15003	\N
9482	0	exit-1600a	2251	16001	\N
9483	0	exit-1600b	2251	16002	\N
9484	0	hallway-2011	2251	20111	\N
9485	0	hallway-2048	2251	20481	\N
9486	0	hallway-2052	2251	20521	\N
9487	0	hallway-2054	2251	20541	\N
9488	0	hallway-2056	2251	20561	\N
9489	0	hallway-2058	2251	20581	\N
9490	0	hallway-2062	2251	20621	\N
9491	0	hallway-2064	2251	20641	\N
9492	0	hallway-2066	2251	20661	\N
9493	0	hallway-2068	2251	20681	\N
9494	0	hallway-2072	2251	20721	\N
9495	0	hallway-2074	2251	20741	\N
9496	0	hallway-2076	2251	20761	\N
9497	0	hallway-2082	2251	20821	\N
9498	0	hallway-2084	2251	20841	\N
9499	0	hallway-2086	2251	20861	\N
9500	0	hallway-2088	2251	20881	\N
9501	0	hallway-2091	2251	20911	\N
9502	0	hallway-2092	2251	20921	\N
9503	0	stairs-2092	2251	20922	\N
9504	0	exit-2092	2251	20923	\N
\.


--
-- TOC entry 3483 (class 0 OID 16468)
-- Dependencies: 234
-- Data for Name: sumo_trace; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sumo_trace (id, version, content_length, file_name, mime_type, absolute_path, description, canonical_path, code_path) FROM stdin;
8051	1	1494819	osmWithStop.xml	text/xml	\N	Teste	\N	ea983b5c-90ef-4743-bd9b-6387b2616ca2
\.


--
-- TOC entry 3484 (class 0 OID 16473)
-- Dependencies: 235
-- Data for Name: tdsensor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tdsensor (id, version, data_type, description, max, min, name, object_id, periodicity) FROM stdin;
1152	2	0	High Humidity Sensor	95	85	highHumSensor	h	30
1151	2	0	Low Temperature Sensor	35	15	lowTempSensor	t	30
10101	1	1	Time-drive current sensor	100	1	acCurrentSensor	c	30
10102	0	1	NH3 Sensor	50	1	nh3Sensor	nh3	30
10103	0	1	CO2 Sensor	1000	400	co2Sensor	co2	30
10104	0	1	O2 Sensor	95	1	o2Sensor	o2	30
10105	0	1	CO Sensor	100	1	coSensor	co	30
10106	0	1	WiFi Sensor	100	1	wifiSensor	w	30
10107	0	0	NFC Sensor	100	1	nfcSensor	c	30
\.


--
-- TOC entry 3490 (class 0 OID 0)
-- Dependencies: 225
-- Name: generator; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.generator', 10300, true);


--
-- TOC entry 3261 (class 2606 OID 16480)
-- Name: actuator_commands actuator_commands_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actuator_commands
    ADD CONSTRAINT actuator_commands_pkey PRIMARY KEY (actuator_id, commands_id);


--
-- TOC entry 3259 (class 2606 OID 16482)
-- Name: actuator actuator_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actuator
    ADD CONSTRAINT actuator_pkey PRIMARY KEY (id);


--
-- TOC entry 3263 (class 2606 OID 16484)
-- Name: command command_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.command
    ADD CONSTRAINT command_pkey PRIMARY KEY (id);


--
-- TOC entry 3265 (class 2606 OID 16486)
-- Name: dataset_file dataset_file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dataset_file
    ADD CONSTRAINT dataset_file_pkey PRIMARY KEY (id);


--
-- TOC entry 3269 (class 2606 OID 16488)
-- Name: device_actuators device_actuators_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_actuators
    ADD CONSTRAINT device_actuators_pkey PRIMARY KEY (device_id, actuators_id);


--
-- TOC entry 3271 (class 2606 OID 16490)
-- Name: device_ed_sensors device_ed_sensors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_ed_sensors
    ADD CONSTRAINT device_ed_sensors_pkey PRIMARY KEY (device_id, ed_sensors_id);


--
-- TOC entry 3273 (class 2606 OID 16492)
-- Name: device_group device_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_group
    ADD CONSTRAINT device_group_pkey PRIMARY KEY (id);


--
-- TOC entry 3275 (class 2606 OID 16494)
-- Name: device_mobile_sensors device_mobile_sensors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_mobile_sensors
    ADD CONSTRAINT device_mobile_sensors_pkey PRIMARY KEY (device_id, mobile_sensors_id);


--
-- TOC entry 3267 (class 2606 OID 16496)
-- Name: device device_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device
    ADD CONSTRAINT device_pkey PRIMARY KEY (id);


--
-- TOC entry 3277 (class 2606 OID 16498)
-- Name: device_td_sensors device_td_sensors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_td_sensors
    ADD CONSTRAINT device_td_sensors_pkey PRIMARY KEY (device_id, td_sensors_id);


--
-- TOC entry 3279 (class 2606 OID 16500)
-- Name: edsensor edsensor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.edsensor
    ADD CONSTRAINT edsensor_pkey PRIMARY KEY (id);


--
-- TOC entry 3281 (class 2606 OID 16502)
-- Name: mobile_sensor mobile_sensor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobile_sensor
    ADD CONSTRAINT mobile_sensor_pkey PRIMARY KEY (id);


--
-- TOC entry 3285 (class 2606 OID 16504)
-- Name: scenario_devices scenario_devices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scenario_devices
    ADD CONSTRAINT scenario_devices_pkey PRIMARY KEY (scenario_id, devices_id);


--
-- TOC entry 3283 (class 2606 OID 16506)
-- Name: scenario scenario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scenario
    ADD CONSTRAINT scenario_pkey PRIMARY KEY (id);


--
-- TOC entry 3289 (class 2606 OID 16508)
-- Name: smart_spec_dataset_file smart_spec_dataset_file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_dataset_file
    ADD CONSTRAINT smart_spec_dataset_file_pkey PRIMARY KEY (id);


--
-- TOC entry 3287 (class 2606 OID 16510)
-- Name: smart_spec_dataset smart_spec_dataset_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_dataset
    ADD CONSTRAINT smart_spec_dataset_pkey PRIMARY KEY (id);


--
-- TOC entry 3291 (class 2606 OID 16512)
-- Name: smart_spec_meta_sensor smart_spec_meta_sensor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_meta_sensor
    ADD CONSTRAINT smart_spec_meta_sensor_pkey PRIMARY KEY (id);


--
-- TOC entry 3293 (class 2606 OID 16514)
-- Name: smart_spec_sensor smart_spec_sensor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_sensor
    ADD CONSTRAINT smart_spec_sensor_pkey PRIMARY KEY (id);


--
-- TOC entry 3295 (class 2606 OID 16516)
-- Name: smart_spec_space smart_spec_space_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_space
    ADD CONSTRAINT smart_spec_space_pkey PRIMARY KEY (id);


--
-- TOC entry 3297 (class 2606 OID 16518)
-- Name: sumo_trace sumo_trace_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sumo_trace
    ADD CONSTRAINT sumo_trace_pkey PRIMARY KEY (id);


--
-- TOC entry 3299 (class 2606 OID 16520)
-- Name: tdsensor tdsensor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tdsensor
    ADD CONSTRAINT tdsensor_pkey PRIMARY KEY (id);


--
-- TOC entry 3300 (class 2606 OID 16521)
-- Name: actuator fk151bgt8xxb4c1eta7uqps1cf9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actuator
    ADD CONSTRAINT fk151bgt8xxb4c1eta7uqps1cf9 FOREIGN KEY (smart_spec_space_id) REFERENCES public.smart_spec_space(id);


--
-- TOC entry 3301 (class 2606 OID 16526)
-- Name: actuator_commands fk1vcv5oyw49398vl08thijpnsh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actuator_commands
    ADD CONSTRAINT fk1vcv5oyw49398vl08thijpnsh FOREIGN KEY (commands_id) REFERENCES public.command(id);


--
-- TOC entry 3305 (class 2606 OID 16531)
-- Name: device_actuators fk3ce2g60nx4a2qmtl3h0empih5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_actuators
    ADD CONSTRAINT fk3ce2g60nx4a2qmtl3h0empih5 FOREIGN KEY (actuators_id) REFERENCES public.actuator(id);


--
-- TOC entry 3307 (class 2606 OID 16536)
-- Name: device_ed_sensors fk5lj2hihcolftai8ubcv87hlxy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_ed_sensors
    ADD CONSTRAINT fk5lj2hihcolftai8ubcv87hlxy FOREIGN KEY (ed_sensors_id) REFERENCES public.edsensor(id);


--
-- TOC entry 3308 (class 2606 OID 16541)
-- Name: device_ed_sensors fk5nqto7d65cnrwabm0hlaepby4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_ed_sensors
    ADD CONSTRAINT fk5nqto7d65cnrwabm0hlaepby4 FOREIGN KEY (device_id) REFERENCES public.device(id);


--
-- TOC entry 3314 (class 2606 OID 16546)
-- Name: mobile_sensor fk69bycl6frr5f767w1halm5otc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobile_sensor
    ADD CONSTRAINT fk69bycl6frr5f767w1halm5otc FOREIGN KEY (sumo_trace_id) REFERENCES public.sumo_trace(id);


--
-- TOC entry 3302 (class 2606 OID 16551)
-- Name: actuator_commands fka3rwa9myyjxbh88o1avrgjyi0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actuator_commands
    ADD CONSTRAINT fka3rwa9myyjxbh88o1avrgjyi0 FOREIGN KEY (actuator_id) REFERENCES public.actuator(id);


--
-- TOC entry 3304 (class 2606 OID 16556)
-- Name: device fka7yf1kwhfa3m14mgatbjex4dw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device
    ADD CONSTRAINT fka7yf1kwhfa3m14mgatbjex4dw FOREIGN KEY (device_group_id) REFERENCES public.device_group(id);


--
-- TOC entry 3315 (class 2606 OID 16561)
-- Name: scenario_devices fkbsxmmvi4e97wdpclwnclxur6l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scenario_devices
    ADD CONSTRAINT fkbsxmmvi4e97wdpclwnclxur6l FOREIGN KEY (devices_id) REFERENCES public.device(id);


--
-- TOC entry 3320 (class 2606 OID 16566)
-- Name: smart_spec_space fkdv8f4catuqe2djo7rpo1dnssh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_space
    ADD CONSTRAINT fkdv8f4catuqe2djo7rpo1dnssh FOREIGN KEY (smart_spec_dataset_id) REFERENCES public.smart_spec_dataset(id);


--
-- TOC entry 3316 (class 2606 OID 16571)
-- Name: scenario_devices fkemqie9aw8xcwqpqpkhylpjuhl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scenario_devices
    ADD CONSTRAINT fkemqie9aw8xcwqpqpkhylpjuhl FOREIGN KEY (scenario_id) REFERENCES public.scenario(id);


--
-- TOC entry 3317 (class 2606 OID 16576)
-- Name: smart_spec_dataset_file fker40jegxd0j6sd3f6u7acncen; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_dataset_file
    ADD CONSTRAINT fker40jegxd0j6sd3f6u7acncen FOREIGN KEY (smart_spec_dataset_id) REFERENCES public.smart_spec_dataset(id);


--
-- TOC entry 3309 (class 2606 OID 16581)
-- Name: device_mobile_sensors fkh3veecb09vi0j1g4a1wx0q8ux; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_mobile_sensors
    ADD CONSTRAINT fkh3veecb09vi0j1g4a1wx0q8ux FOREIGN KEY (device_id) REFERENCES public.device(id);


--
-- TOC entry 3319 (class 2606 OID 16586)
-- Name: smart_spec_sensor fkhcjyyfyuohsjdwta3llncmiqk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_sensor
    ADD CONSTRAINT fkhcjyyfyuohsjdwta3llncmiqk FOREIGN KEY (smart_spec_dataset_id) REFERENCES public.smart_spec_dataset(id);


--
-- TOC entry 3310 (class 2606 OID 16591)
-- Name: device_mobile_sensors fkifto1kbpk03kibsa6huumghpj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_mobile_sensors
    ADD CONSTRAINT fkifto1kbpk03kibsa6huumghpj FOREIGN KEY (mobile_sensors_id) REFERENCES public.mobile_sensor(id);


--
-- TOC entry 3306 (class 2606 OID 16596)
-- Name: device_actuators fkk6mi7kj2elobk03cgborebiof; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_actuators
    ADD CONSTRAINT fkk6mi7kj2elobk03cgborebiof FOREIGN KEY (device_id) REFERENCES public.device(id);


--
-- TOC entry 3313 (class 2606 OID 16601)
-- Name: edsensor fklyrc90a7lf6iqgfgmh07ecmfc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.edsensor
    ADD CONSTRAINT fklyrc90a7lf6iqgfgmh07ecmfc FOREIGN KEY (smart_spec_sensor_id) REFERENCES public.smart_spec_sensor(id);


--
-- TOC entry 3318 (class 2606 OID 16606)
-- Name: smart_spec_meta_sensor fkmjopynwyry0o9kk5dp9cwkrha; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_meta_sensor
    ADD CONSTRAINT fkmjopynwyry0o9kk5dp9cwkrha FOREIGN KEY (smart_spec_dataset_id) REFERENCES public.smart_spec_dataset(id);


--
-- TOC entry 3311 (class 2606 OID 16611)
-- Name: device_td_sensors fko2cmr2khcfdofbu9xtt187nx6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_td_sensors
    ADD CONSTRAINT fko2cmr2khcfdofbu9xtt187nx6 FOREIGN KEY (td_sensors_id) REFERENCES public.tdsensor(id);


--
-- TOC entry 3303 (class 2606 OID 16616)
-- Name: dataset_file fkou3ak6mnvx34s18mgw8f2xrfm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dataset_file
    ADD CONSTRAINT fkou3ak6mnvx34s18mgw8f2xrfm FOREIGN KEY (smart_spec_dataset_id) REFERENCES public.smart_spec_dataset(id);


--
-- TOC entry 3312 (class 2606 OID 16621)
-- Name: device_td_sensors fkrqlchh3kbsik8f5a5yw53qkbe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_td_sensors
    ADD CONSTRAINT fkrqlchh3kbsik8f5a5yw53qkbe FOREIGN KEY (device_id) REFERENCES public.device(id);


-- Completed on 2024-01-18 17:16:54

--
-- PostgreSQL database dump complete
--

