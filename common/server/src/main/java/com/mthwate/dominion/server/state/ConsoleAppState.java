package com.mthwate.dominion.server.state;

import com.mthwate.dominion.server.command.CommandUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author mthwate
 */
@Slf4j
public class ConsoleAppState extends ServerAppState {

	private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	@Override
	public void update(float tpf) {
		try {
			while (stdin.ready()) {
				CommandUtils.run(sapp, stdin.readLine());
			}
		} catch (IOException e) {
			log.error("Failed to read from stdin", e);
		}
	}

}