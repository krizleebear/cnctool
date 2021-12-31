# cnctool
tools accomodating my cnc

## Tool length log
Parse EdingCNC logs and extract the measured tool length. This will help setting the approximate length for making the rapid move downwards to the tool length sensor.

Example output:
```
T#	Len	
T1	30	1/8" x 22mm Zweischneider gerade	29.155	29.074	29.088	29.091	29.647	
T2	40	6x32 Zweischneider gerade	39.55	39.313	38.932	38.274	38.475	38.121	37.678	37.355	37.355	37.042	36.552	36.202	35.974	35.416	36.049	37.766	37.724	34.277	34.264	34.342	
T3	25	2x15mm Zweischneider gerade	22.2	22.327	22.1	22.807	23.56	23.557	23.555	24.742	24.663	24.017	24.077	22.506	22.872	22.923	22.839	22.614	22.49	22.381	22.301	22.254	
T4	41	4mm x 32mm Zweischneider gerade	40.152	
T5	24	1/8" x 15mm Zweischneider gerade	21.799	22.9	21.775	22.708	23.191	23.124	23.989	22.33	21.042	22.561	20.797	
T6	32	20mm Bosch Planfräser	29.011	28.957	28.824	31.233	30.138	30.175	30.066	29.899	29.91	30.388	31.816	28.679	28.606	28.44	28.531	28.606	27.825	27.7	28.067	
T8	36	8x32 Zweischneider gerade	35.777	34.713	
T9	45	abgebrochen 10° Gravur	43.503	43.489	43.302	43.457	43.235	43.333	44.874	44.486	42.84	42.794	42.879	42.9	42.95	44.079	43.795	42.979	43.026	43.151	42.939	42.928	43.05	42.915	42.678	42.973	42.942	
T10	40	6x32mm Ball Endmill	37.836	39.636	39.227	39.225	38.574	39.3	38.853	38.342	38.225	37.296	
T11	47	8x12mm Ball Endmill	36.991	36.952	46.238	34.372	
T15	49	Schruppfräser 8mm	48.733	48.661	48.686	47.309	47.34	
T21	25	Spiralverzahnt 1,5mm (grün)	24.249	24.11	24.107	
T33	25	Spiralbohrer 3mm	24.1	
T34	24	Spiralbohrer 2,7x12mm gold	19.424	23.939	22.908	
T42	30	4mm x 22mm Zweischneider gerade	29.917	29.808	25.834	
T44	29	20° Gravur 4mm	28.006	27.925	28.276	27.994	27.9	
T62	30	6x22 Zweischneider gerade	27.814	27.757	29.222	27.264	27.149	27.139	27.1	26.824	26.539	26.375	26.324	25.145	25.094	25.086	27.286	27.198	27.065	27.056	27.417	27.254	27.14	27.272	27.025	26.987	26.648	26.576	26.451	26.275	26.275	25.053	24.275	27.342	27.148	27.006	26.934	26.654	
T65	54	6x45 Sorotec Holzfräser Z2	53.378	53.516	53.116	53.205	52.903	53.308	51.39	51.634	
T84	33	8mm 2x45° Fase	32.825	
T86	75	8x62 Zweischneider gerade	74.3	
T89	27	PCD 8mm 90° Fase	23.602	23.552	23.699	23.558	23.575	23.703	24.927	25.069	24.867	24.7	25.047	24.808	24.803	26.333	24.83	24.933	25.046	24.866	24.808	24.825	24.874	23.308	23.334	22.562	22.476	22.942	23.065	22.931	22.619	
T92	21	Zinkenfräser 14,3 mm	20.472
```

## Detect most frequently used tools
Parse EdingCNC logs and detect, which tools are being used most frequently. This should help to identify the amount of tool holders you really need.

Example output:

```
Logs go back until: 2021-10-29
Most frequently used tools: 
36x tool(s) [62]
29x tool(s) [89]
25x tool(s) [9]
20x tool(s) [2, 3]
19x tool(s) [6]
11x tool(s) [5]
10x tool(s) [10]
8x tool(s) [65]
5x tool(s) [1, 15, 44]
4x tool(s) [11]
3x tool(s) [21, 34, 42]
2x tool(s) [8]
1x tool(s) [4, 33, 84, 86, 92]
```
