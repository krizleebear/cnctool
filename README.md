# cnctool
tools accomodating my cnc

## Tool length log
Parse EdingCNC logs and extract the measured tool length. This will help setting the approximate length for making the rapid move downwards to the tool length sensor.

Example output:
```
T#	Len	
T1	30	29.155	29.074	29.088	29.091	29.647	
T2	40	39.55	39.313	38.932	38.274	38.475	38.121	37.678	37.355	37.355	37.042	36.552	36.202	35.974	35.416	36.049	37.766	37.724	34.277	34.264	34.342	
T3	25	22.2	22.327	22.1	22.807	23.56	23.557	23.555	24.742	24.663	24.017	24.077	22.506	22.872	22.923	22.839	22.614	22.49	22.381	22.301	22.254	
T4	41	40.152	
T5	24	21.799	22.9	21.775	22.708	23.191	23.124	23.989	22.33	21.042	22.561	20.797	
T6	32	29.011	28.957	28.824	31.233	30.138	30.175	30.066	29.899	29.91	30.388	31.816	28.679	28.606	28.44	28.531	28.606	27.825	27.7	28.067	
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
