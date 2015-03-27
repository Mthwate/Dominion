package com.mthwate.dominion.server.state;

import com.mthwate.dominion.server.command.CommandUtils;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

/**
 * @author mthwate
 */
@Log
public class ConsoleAppState extends ServerAppState {

	private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	@Override
	public void update(float tpf) {
		try {
			while (stdin.ready()) {
				CommandUtils.run(sapp, stdin.readLine());
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, "Failed to read from stdin", e);
		}
	}

}