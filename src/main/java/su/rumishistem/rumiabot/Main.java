package su.rumishistem.rumiabot;

import static com.rumisystem.rumi_java_lib.LOG_PRINT.Main.LOG;
import static su.rumishistem.rumiabot.Main.CONFIG_DATA;

import java.io.File;
import java.io.PrintStream;

import com.rumisystem.rumi_java_lib.ArrayNode;
import com.rumisystem.rumi_java_lib.CONFIG;
import com.rumisystem.rumi_java_lib.SQL;
import com.rumisystem.rumi_java_lib.LOG_PRINT.LOG_TYPE;
import com.rumisystem.rumi_java_lib.Misskey.MisskeyClient;

import net.dv8tion.jda.api.JDA;
import su.rumishistem.rumiabot.Discord.DiscordBOTMain;
import su.rumishistem.rumiabot.MODULE.ISHITEGAWA.ISHITEGAWA_DAM;
import su.rumishistem.rumiabot.Misskey.MisskeyBotMain;

public class Main {
	public static ArrayNode CONFIG_DATA = null;
	public static JDA DISCORD_BOT = null;
	public static MisskeyClient MisskeyBOT = null;

	public static void main(String[] args) {
		try {
			LOG(LOG_TYPE.INFO, "    ____                  _       ____  ____  ______    ");
			LOG(LOG_TYPE.INFO, "   / __ \\__  ______ ___  (_)___ _/ __ )/ __ \\/_  __/  ");
			LOG(LOG_TYPE.INFO, "  / /_/ / / / / __ `__ \\/ / __ `/ __  / / / / / /      ");
			LOG(LOG_TYPE.INFO, " / _, _/ /_/ / / / / / / / /_/ / /_/ / /_/ / / /        ");
			LOG(LOG_TYPE.INFO, "/_/ |_|\\__,_/_/ /_/ /_/_/\\__,_/_____/\\____/ /_/      ");
			LOG(LOG_TYPE.INFO, "V1.1");

			/*
			//SLF4Jのエラーを潰すためにごにょごにょ
			PrintStream SYSTEM_ERR_PS = System.err;
			System.setErr(new PrintStream(new java.io.OutputStream() {
				@Override
				public void write(int b) {
					//何もしない
				}
			}));*/
			
			LOG(LOG_TYPE.PROCESS, "Config load...");
			if (new File("./Config.ini").exists()) {
				CONFIG_DATA = new CONFIG().DATA;
				LOG(LOG_TYPE.PROCESS_END_OK, "");
			} else {
				LOG(LOG_TYPE.PROCESS_END_FAILED, "Config.ini GA NAI!!!!!!!");
				System.exit(1);
			}
			
			//SQL用意
			SQL.CONNECT(
				CONFIG_DATA.get("SQL").asString("HOST"),
				CONFIG_DATA.get("SQL").asString("PORT"),
				CONFIG_DATA.get("SQL").asString("DB"),
				CONFIG_DATA.get("SQL").asString("USER"),
				CONFIG_DATA.get("SQL").asString("PASS")
			);

			//石手川ダムを起動
			ISHITEGAWA_DAM.Main();

			//DiscordBOT作成
			DiscordBOTMain.START_DISCORD_BOT();

			//MisskeyBOT作成
			new Thread(new Runnable() {
				@Override
				public void run() {
					MisskeyBotMain.Main();
				}
			}).start();

			HTTP.Main();

			//SLF4Jのエラーを潰すためにエラー出力を迂回させていたのを元に戻す
			//System.setErr(SYSTEM_ERR_PS);
		} catch (Exception EX) {
			EX.printStackTrace();
		}
	}
}
