from HTTP.HTTP_SERVER import CREATE_HTTP_SERVER
from MODULES_PYTHON.PRINT import PRINT
from SQL import CONNECT
from MODULES_PYTHON.CONFIG import CONFIG_LOAD
from PROCESS_WS import PWS_MAIN
import asyncio

async def main():
	PRINT("RumiaBOT Python Script\n")

	CONFIG_DATA:dict = CONFIG_LOAD()
	
	if(CONFIG_DATA["SQL"]["SQL_CONNECT"]):
		CONNECT(
			CONFIG_DATA["SQL"]["SQL_HOST"],
			CONFIG_DATA["SQL"]["SQL_USER"],
			CONFIG_DATA["SQL"]["SQL_PASS"],
			CONFIG_DATA["SQL"]["SQL_DB"]
		)

	await PWS_MAIN()
	#CREATE_HTTP_SERVER("0.0.0.0", 3000)

#main関数を実行
asyncio.run(main())