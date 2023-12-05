--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2 (Debian 15.2-1.pgdg110+1)
-- Dumped by pg_dump version 15.2

-- Started on 2023-12-05 11:07:20

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
-- TOC entry 214 (class 1259 OID 16389)
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
-- TOC entry 215 (class 1259 OID 16396)
-- Name: actuator_commands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actuator_commands (
    actuator_id bigint NOT NULL,
    commands_id bigint NOT NULL
);


ALTER TABLE public.actuator_commands OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16401)
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
-- TOC entry 230 (class 1259 OID 24576)
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
-- TOC entry 217 (class 1259 OID 16408)
-- Name: device; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device (
    id bigint NOT NULL,
    version integer NOT NULL,
    description character varying(255),
    device_id character varying(255),
    device_group_id bigint NOT NULL
);


ALTER TABLE public.device OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16415)
-- Name: device_actuators; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device_actuators (
    device_id bigint NOT NULL,
    actuators_id bigint NOT NULL
);


ALTER TABLE public.device_actuators OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16420)
-- Name: device_ed_sensors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device_ed_sensors (
    device_id bigint NOT NULL,
    ed_sensors_id bigint NOT NULL
);


ALTER TABLE public.device_ed_sensors OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16435)
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
-- TOC entry 220 (class 1259 OID 16425)
-- Name: device_mobile_sensors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device_mobile_sensors (
    device_id bigint NOT NULL,
    mobile_sensors_id bigint NOT NULL
);


ALTER TABLE public.device_mobile_sensors OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16430)
-- Name: device_td_sensors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device_td_sensors (
    device_id bigint NOT NULL,
    td_sensors_id bigint NOT NULL
);


ALTER TABLE public.device_td_sensors OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16442)
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
-- TOC entry 229 (class 1259 OID 16484)
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
-- TOC entry 224 (class 1259 OID 16450)
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
-- TOC entry 225 (class 1259 OID 16457)
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
-- TOC entry 226 (class 1259 OID 16464)
-- Name: scenario_devices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scenario_devices (
    scenario_id bigint NOT NULL,
    devices_id bigint NOT NULL
);


ALTER TABLE public.scenario_devices OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 24583)
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
-- TOC entry 235 (class 1259 OID 32798)
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
-- TOC entry 232 (class 1259 OID 32768)
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
-- TOC entry 233 (class 1259 OID 32773)
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
-- TOC entry 234 (class 1259 OID 32778)
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
-- TOC entry 227 (class 1259 OID 16469)
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
-- TOC entry 228 (class 1259 OID 16476)
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
-- TOC entry 3462 (class 0 OID 16389)
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
-- TOC entry 3463 (class 0 OID 16396)
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
-- TOC entry 3464 (class 0 OID 16401)
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
-- TOC entry 3478 (class 0 OID 24576)
-- Dependencies: 230
-- Data for Name: dataset_file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dataset_file (id, version, content_length, description, file_name, mime_type, smart_spec_dataset_id) FROM stdin;
\.


--
-- TOC entry 3465 (class 0 OID 16408)
-- Dependencies: 217
-- Data for Name: device; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device (id, version, description, device_id, device_group_id) FROM stdin;
1303	0	Temperature Sensor 001 001 001	temperatureSensor001001001	1051
1304	1	Temperature Sensor 001 002 001	temperatureSensor001002001	1051
1305	0	Lamp 001 001 001	lamp001001001	1053
1306	0	Lamp 001 001 002	lamp001001002	1053
1307	0	Air Conditioner 001 001 001	airConditioner001001001	1052
1308	0	Air Conditioner 001 002 001	airConditioner001002001	1052
1309	0	Door 001 001 001	door001001001	1054
1310	0	Door 001 002 001	door001002001	1054
8151	0	dsfsdfsfs	vehicle010101	1058
9802	0	ED Wifi Sensor 001	edWifiSensor001	9001
9601	2	ED Temperature Sensor 001	edTempSensor001	1051
9801	1	ED Temperature Sensor 002	edTempSensor002	1051
9803	0	ED Wifi Sensor 002	edWifiSensor002	9001
9804	0	ED Door Sensor 001	edDoorSensor001	9751
9805	0	ED Door Sensor 002	edDoorSensor002	9751
9901	0	ED Lamp Actuator main lobby 1	edLampActuator001	1053
\.


--
-- TOC entry 3466 (class 0 OID 16415)
-- Dependencies: 218
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
\.


--
-- TOC entry 3467 (class 0 OID 16420)
-- Dependencies: 219
-- Data for Name: device_ed_sensors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device_ed_sensors (device_id, ed_sensors_id) FROM stdin;
9601	9551
9801	9701
9802	9702
9803	9703
9804	9704
9805	9705
\.


--
-- TOC entry 3470 (class 0 OID 16435)
-- Dependencies: 222
-- Data for Name: device_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device_group (id, version, api_key, description, name) FROM stdin;
1051	0	scggokgpepnvsb2uv4s40d59o1	Group of UHT sensors	TemperatureSensor
1052	0	scggokgpepnvsb2uv4s40d59o2	Group of IR transmitters for remote Air Conditioner control	AirConditioner
1053	0	scggokgpepnvsb2uv4s40d59o3	Group of smarts switches	Lamp
1055	0	scggokgpepnvsb2uv4s40d59o5	Group of NFC card readers	NFCReader
1056	0	scggokgpepnvsb2uv4s40d59o6	Group of Current sensors	CurrentSensor
1058	0	scggokgpepnvsb2uv4s40d59o9	Group of Vehicles	Vehicle
1057	1	scggokgpepnvsb2uv4s40d59o7	Group of K-300 Portable ammonia gas Detector Digital	GasDetector
9001	0	scggokgpepnvsb2uv4s40d59o8	Group of Wifi AP Sensors	WifiSensor
1054	1	scggokgpepnvsb2uv4s40d59o4	Group of lockers	DoorLocker
9751	0	scggokgpepnvsb2uv4s40d5910	Group of door contact sensors	DoorSensor
\.


--
-- TOC entry 3468 (class 0 OID 16425)
-- Dependencies: 220
-- Data for Name: device_mobile_sensors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device_mobile_sensors (device_id, mobile_sensors_id) FROM stdin;
8151	8101
\.


--
-- TOC entry 3469 (class 0 OID 16430)
-- Dependencies: 221
-- Data for Name: device_td_sensors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device_td_sensors (device_id, td_sensors_id) FROM stdin;
1303	1151
1303	1152
1304	1151
1304	1152
\.


--
-- TOC entry 3471 (class 0 OID 16442)
-- Dependencies: 223
-- Data for Name: edsensor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.edsensor (id, version, description, name, object_id, smart_spec_sensor_id) FROM stdin;
9701	1	Template from temperature sensor from main-lobby1	edtempTemp1101	t	9371
9551	1	Template from temperature sensor 1100	edtempTemp1100	t	9374
9702	2	Template from Wifi sensor from main-lobby1	edwifiTemp1100	w	9351
9703	0	Template Wifi Sensor from main-lobb2	edwifiTemp1101	w	9353
9704	0	Template Door Sensor from 12001	eddoorTemp1100	d	9366
9705	0	Template Door Sensor from 12002	eddoorTemp1101	d	9367
\.


--
-- TOC entry 3472 (class 0 OID 16450)
-- Dependencies: 224
-- Data for Name: mobile_sensor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mobile_sensor (id, version, description, mobile_id, name, object_id, periodicity, sumo_trace_id) FROM stdin;
8101	0	v1	f_0.0	v1	vehicle	2	8051
\.


--
-- TOC entry 3473 (class 0 OID 16457)
-- Dependencies: 225
-- Data for Name: scenario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scenario (id, version, description, name) FROM stdin;
1352	0	Test Scenario	Scenario 1
8201	1	sdasdasdas	Scenario 2
9951	0	Testing ED Sensors and Actuators	Scenario 3
\.


--
-- TOC entry 3474 (class 0 OID 16464)
-- Dependencies: 226
-- Data for Name: scenario_devices; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scenario_devices (scenario_id, devices_id) FROM stdin;
1352	1307
1352	1308
1352	1309
1352	1310
1352	1304
1352	1306
1352	1303
1352	1305
8201	8151
9951	9801
9951	9802
9951	9601
9951	9804
9951	9901
9951	9805
9951	9803
\.


--
-- TOC entry 3479 (class 0 OID 24583)
-- Dependencies: 231
-- Data for Name: smart_spec_dataset; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.smart_spec_dataset (id, version, code_path, description, name, status, base_start_date) FROM stdin;
2251	1	204f2dc6-6b53-47d4-8dad-e1f9c2817850	Testing Smart Building	Smart Building	NotAvailable	2020-01-06 00:00:00
\.


--
-- TOC entry 3483 (class 0 OID 32798)
-- Dependencies: 235
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
-- TOC entry 3480 (class 0 OID 32768)
-- Dependencies: 232
-- Data for Name: smart_spec_meta_sensor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.smart_spec_meta_sensor (id, version, description, smart_spec_dataset_id, json_id) FROM stdin;
9304	0	WiFi Sensor	2251	1
9305	0	Door Sensor	2251	2
9306	0	Temperature Sensors	2251	3
\.


--
-- TOC entry 3481 (class 0 OID 32773)
-- Dependencies: 233
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
-- TOC entry 3482 (class 0 OID 32778)
-- Dependencies: 234
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
-- TOC entry 3475 (class 0 OID 16469)
-- Dependencies: 227
-- Data for Name: sumo_trace; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sumo_trace (id, version, content_length, file_name, mime_type, absolute_path, description, canonical_path, code_path) FROM stdin;
8051	1	1494819	osmWithStop.xml	text/xml	\N	Teste	\N	ea983b5c-90ef-4743-bd9b-6387b2616ca2
\.


--
-- TOC entry 3476 (class 0 OID 16476)
-- Dependencies: 228
-- Data for Name: tdsensor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tdsensor (id, version, data_type, description, max, min, name, object_id, periodicity) FROM stdin;
1152	2	0	High Humidity Sensor	95	85	highHumSensor	h	30
1151	2	0	Low Temperature Sensor	35	15	lowTempSensor	t	30
\.


--
-- TOC entry 3489 (class 0 OID 0)
-- Dependencies: 229
-- Name: generator; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.generator', 10050, true);


--
-- TOC entry 3260 (class 2606 OID 16400)
-- Name: actuator_commands actuator_commands_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actuator_commands
    ADD CONSTRAINT actuator_commands_pkey PRIMARY KEY (actuator_id, commands_id);


--
-- TOC entry 3258 (class 2606 OID 16395)
-- Name: actuator actuator_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actuator
    ADD CONSTRAINT actuator_pkey PRIMARY KEY (id);


--
-- TOC entry 3262 (class 2606 OID 16407)
-- Name: command command_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.command
    ADD CONSTRAINT command_pkey PRIMARY KEY (id);


--
-- TOC entry 3288 (class 2606 OID 24582)
-- Name: dataset_file dataset_file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dataset_file
    ADD CONSTRAINT dataset_file_pkey PRIMARY KEY (id);


--
-- TOC entry 3266 (class 2606 OID 16419)
-- Name: device_actuators device_actuators_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_actuators
    ADD CONSTRAINT device_actuators_pkey PRIMARY KEY (device_id, actuators_id);


--
-- TOC entry 3268 (class 2606 OID 16424)
-- Name: device_ed_sensors device_ed_sensors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_ed_sensors
    ADD CONSTRAINT device_ed_sensors_pkey PRIMARY KEY (device_id, ed_sensors_id);


--
-- TOC entry 3274 (class 2606 OID 16441)
-- Name: device_group device_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_group
    ADD CONSTRAINT device_group_pkey PRIMARY KEY (id);


--
-- TOC entry 3270 (class 2606 OID 16429)
-- Name: device_mobile_sensors device_mobile_sensors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_mobile_sensors
    ADD CONSTRAINT device_mobile_sensors_pkey PRIMARY KEY (device_id, mobile_sensors_id);


--
-- TOC entry 3264 (class 2606 OID 16414)
-- Name: device device_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device
    ADD CONSTRAINT device_pkey PRIMARY KEY (id);


--
-- TOC entry 3272 (class 2606 OID 16434)
-- Name: device_td_sensors device_td_sensors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_td_sensors
    ADD CONSTRAINT device_td_sensors_pkey PRIMARY KEY (device_id, td_sensors_id);


--
-- TOC entry 3276 (class 2606 OID 16449)
-- Name: edsensor edsensor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.edsensor
    ADD CONSTRAINT edsensor_pkey PRIMARY KEY (id);


--
-- TOC entry 3278 (class 2606 OID 16456)
-- Name: mobile_sensor mobile_sensor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobile_sensor
    ADD CONSTRAINT mobile_sensor_pkey PRIMARY KEY (id);


--
-- TOC entry 3282 (class 2606 OID 16468)
-- Name: scenario_devices scenario_devices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scenario_devices
    ADD CONSTRAINT scenario_devices_pkey PRIMARY KEY (scenario_id, devices_id);


--
-- TOC entry 3280 (class 2606 OID 16463)
-- Name: scenario scenario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scenario
    ADD CONSTRAINT scenario_pkey PRIMARY KEY (id);


--
-- TOC entry 3298 (class 2606 OID 32804)
-- Name: smart_spec_dataset_file smart_spec_dataset_file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_dataset_file
    ADD CONSTRAINT smart_spec_dataset_file_pkey PRIMARY KEY (id);


--
-- TOC entry 3290 (class 2606 OID 24589)
-- Name: smart_spec_dataset smart_spec_dataset_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_dataset
    ADD CONSTRAINT smart_spec_dataset_pkey PRIMARY KEY (id);


--
-- TOC entry 3292 (class 2606 OID 32772)
-- Name: smart_spec_meta_sensor smart_spec_meta_sensor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_meta_sensor
    ADD CONSTRAINT smart_spec_meta_sensor_pkey PRIMARY KEY (id);


--
-- TOC entry 3294 (class 2606 OID 32777)
-- Name: smart_spec_sensor smart_spec_sensor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_sensor
    ADD CONSTRAINT smart_spec_sensor_pkey PRIMARY KEY (id);


--
-- TOC entry 3296 (class 2606 OID 32782)
-- Name: smart_spec_space smart_spec_space_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_space
    ADD CONSTRAINT smart_spec_space_pkey PRIMARY KEY (id);


--
-- TOC entry 3284 (class 2606 OID 16475)
-- Name: sumo_trace sumo_trace_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sumo_trace
    ADD CONSTRAINT sumo_trace_pkey PRIMARY KEY (id);


--
-- TOC entry 3286 (class 2606 OID 16483)
-- Name: tdsensor tdsensor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tdsensor
    ADD CONSTRAINT tdsensor_pkey PRIMARY KEY (id);


--
-- TOC entry 3299 (class 2606 OID 57344)
-- Name: actuator fk151bgt8xxb4c1eta7uqps1cf9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actuator
    ADD CONSTRAINT fk151bgt8xxb4c1eta7uqps1cf9 FOREIGN KEY (smart_spec_space_id) REFERENCES public.smart_spec_space(id);


--
-- TOC entry 3300 (class 2606 OID 16485)
-- Name: actuator_commands fk1vcv5oyw49398vl08thijpnsh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actuator_commands
    ADD CONSTRAINT fk1vcv5oyw49398vl08thijpnsh FOREIGN KEY (commands_id) REFERENCES public.command(id);


--
-- TOC entry 3303 (class 2606 OID 16500)
-- Name: device_actuators fk3ce2g60nx4a2qmtl3h0empih5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_actuators
    ADD CONSTRAINT fk3ce2g60nx4a2qmtl3h0empih5 FOREIGN KEY (actuators_id) REFERENCES public.actuator(id);


--
-- TOC entry 3305 (class 2606 OID 16510)
-- Name: device_ed_sensors fk5lj2hihcolftai8ubcv87hlxy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_ed_sensors
    ADD CONSTRAINT fk5lj2hihcolftai8ubcv87hlxy FOREIGN KEY (ed_sensors_id) REFERENCES public.edsensor(id);


--
-- TOC entry 3306 (class 2606 OID 16515)
-- Name: device_ed_sensors fk5nqto7d65cnrwabm0hlaepby4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_ed_sensors
    ADD CONSTRAINT fk5nqto7d65cnrwabm0hlaepby4 FOREIGN KEY (device_id) REFERENCES public.device(id);


--
-- TOC entry 3312 (class 2606 OID 16540)
-- Name: mobile_sensor fk69bycl6frr5f767w1halm5otc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobile_sensor
    ADD CONSTRAINT fk69bycl6frr5f767w1halm5otc FOREIGN KEY (sumo_trace_id) REFERENCES public.sumo_trace(id);


--
-- TOC entry 3301 (class 2606 OID 16490)
-- Name: actuator_commands fka3rwa9myyjxbh88o1avrgjyi0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actuator_commands
    ADD CONSTRAINT fka3rwa9myyjxbh88o1avrgjyi0 FOREIGN KEY (actuator_id) REFERENCES public.actuator(id);


--
-- TOC entry 3302 (class 2606 OID 16495)
-- Name: device fka7yf1kwhfa3m14mgatbjex4dw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device
    ADD CONSTRAINT fka7yf1kwhfa3m14mgatbjex4dw FOREIGN KEY (device_group_id) REFERENCES public.device_group(id);


--
-- TOC entry 3313 (class 2606 OID 16545)
-- Name: scenario_devices fkbsxmmvi4e97wdpclwnclxur6l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scenario_devices
    ADD CONSTRAINT fkbsxmmvi4e97wdpclwnclxur6l FOREIGN KEY (devices_id) REFERENCES public.device(id);


--
-- TOC entry 3318 (class 2606 OID 32825)
-- Name: smart_spec_space fkdv8f4catuqe2djo7rpo1dnssh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_space
    ADD CONSTRAINT fkdv8f4catuqe2djo7rpo1dnssh FOREIGN KEY (smart_spec_dataset_id) REFERENCES public.smart_spec_dataset(id);


--
-- TOC entry 3314 (class 2606 OID 16550)
-- Name: scenario_devices fkemqie9aw8xcwqpqpkhylpjuhl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scenario_devices
    ADD CONSTRAINT fkemqie9aw8xcwqpqpkhylpjuhl FOREIGN KEY (scenario_id) REFERENCES public.scenario(id);


--
-- TOC entry 3319 (class 2606 OID 32805)
-- Name: smart_spec_dataset_file fker40jegxd0j6sd3f6u7acncen; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_dataset_file
    ADD CONSTRAINT fker40jegxd0j6sd3f6u7acncen FOREIGN KEY (smart_spec_dataset_id) REFERENCES public.smart_spec_dataset(id);


--
-- TOC entry 3307 (class 2606 OID 16525)
-- Name: device_mobile_sensors fkh3veecb09vi0j1g4a1wx0q8ux; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_mobile_sensors
    ADD CONSTRAINT fkh3veecb09vi0j1g4a1wx0q8ux FOREIGN KEY (device_id) REFERENCES public.device(id);


--
-- TOC entry 3317 (class 2606 OID 32815)
-- Name: smart_spec_sensor fkhcjyyfyuohsjdwta3llncmiqk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_sensor
    ADD CONSTRAINT fkhcjyyfyuohsjdwta3llncmiqk FOREIGN KEY (smart_spec_dataset_id) REFERENCES public.smart_spec_dataset(id);


--
-- TOC entry 3308 (class 2606 OID 16520)
-- Name: device_mobile_sensors fkifto1kbpk03kibsa6huumghpj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_mobile_sensors
    ADD CONSTRAINT fkifto1kbpk03kibsa6huumghpj FOREIGN KEY (mobile_sensors_id) REFERENCES public.mobile_sensor(id);


--
-- TOC entry 3304 (class 2606 OID 16505)
-- Name: device_actuators fkk6mi7kj2elobk03cgborebiof; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_actuators
    ADD CONSTRAINT fkk6mi7kj2elobk03cgborebiof FOREIGN KEY (device_id) REFERENCES public.device(id);


--
-- TOC entry 3311 (class 2606 OID 49152)
-- Name: edsensor fklyrc90a7lf6iqgfgmh07ecmfc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.edsensor
    ADD CONSTRAINT fklyrc90a7lf6iqgfgmh07ecmfc FOREIGN KEY (smart_spec_sensor_id) REFERENCES public.smart_spec_sensor(id);


--
-- TOC entry 3316 (class 2606 OID 32810)
-- Name: smart_spec_meta_sensor fkmjopynwyry0o9kk5dp9cwkrha; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.smart_spec_meta_sensor
    ADD CONSTRAINT fkmjopynwyry0o9kk5dp9cwkrha FOREIGN KEY (smart_spec_dataset_id) REFERENCES public.smart_spec_dataset(id);


--
-- TOC entry 3309 (class 2606 OID 16530)
-- Name: device_td_sensors fko2cmr2khcfdofbu9xtt187nx6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_td_sensors
    ADD CONSTRAINT fko2cmr2khcfdofbu9xtt187nx6 FOREIGN KEY (td_sensors_id) REFERENCES public.tdsensor(id);


--
-- TOC entry 3315 (class 2606 OID 24590)
-- Name: dataset_file fkou3ak6mnvx34s18mgw8f2xrfm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dataset_file
    ADD CONSTRAINT fkou3ak6mnvx34s18mgw8f2xrfm FOREIGN KEY (smart_spec_dataset_id) REFERENCES public.smart_spec_dataset(id);


--
-- TOC entry 3310 (class 2606 OID 16535)
-- Name: device_td_sensors fkrqlchh3kbsik8f5a5yw53qkbe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device_td_sensors
    ADD CONSTRAINT fkrqlchh3kbsik8f5a5yw53qkbe FOREIGN KEY (device_id) REFERENCES public.device(id);


-- Completed on 2023-12-05 11:07:21

--
-- PostgreSQL database dump complete
--

