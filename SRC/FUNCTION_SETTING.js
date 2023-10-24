/**
 * 機能設定
 */
import { SQL_OBJ } from "./Main.js";
import { CONFIG } from "./MODULES/CONFIG.js";
export class FUNCTION_SETTING {
	async LOAD() {
		if (CONFIG.SQL_CONNECT) {
			try {
				const RESULT = await SQL_OBJ.SCRIPT_RUN("SELECT * FROM `CONFIG`", []);
				return RESULT;
			} catch (EX) {
				console.error(EX);
				return [];
			}
		} else {
			return [];
		}
	}
}
