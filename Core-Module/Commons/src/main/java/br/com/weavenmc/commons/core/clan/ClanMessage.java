package br.com.weavenmc.commons.core.clan;

import lombok.Getter;
/**
 * Copyright (C) WeavenMC. All Rights Reserved.
 *
 * Unauthorized copying, modification, distribution, or use of this file,
 * via any medium, is strictly prohibited without explicit permission
 * from the copyright holder.
 *
 * Distribution and use for educational purposes are permitted.
 * Commercial use, resale, or any form of monetized distribution
 * is strictly prohibited under GNU License terms and applicable
 * copyright laws.
 *
 * Violators may be subject to legal action.
 */

@Getter
public class ClanMessage {
	
	private String userMessageResponser = "-";
	private String clanName = "-";
	private String oldAdmin = "-";
	private String oldMember = "-";
	private String userToLeave = "-";
	private int moneyUsed = 0;
	private String userKicked = "-";
	private String userJoined = "-";
	private String clanTag = "-";
	private String messageTypeString = "-";
	
	public ClanMessage writeResponse(String r) {
		this.userMessageResponser = r;
		return this;
	}
	
	public ClanMessage writeOldAdmin(String r) {
		this.oldAdmin = r;
		return this;
	}
	
	public ClanMessage writeOldMember(String r) {
		this.oldMember = r;
		return this;
	}
	
	public ClanMessage writeClanName(String r) {
		this.clanName = r;
		return this;
	}
	
	public ClanMessage writeUserToLeave(String r) {
		this.userToLeave = r;
		return this;
	}
	
	public ClanMessage writeMoneyUsed(int r) {
		this.moneyUsed = r;
		return this;
	}
	
	public ClanMessage writeUserKicked(String r) {
		this.userKicked = r;
		return this;
	}
	
	public ClanMessage writeUserJoined(String r) {
		this.userJoined = r;
		return this;
	}
	
	public ClanMessage writeClanTag(String r) {
		this.clanTag = r;
		return this;
	}
	
	public ClanMessage writeType(MessageType r) {
		this.messageTypeString = r.name();
		return this;
	}
 	
	public MessageType readType() {
		return MessageType.valueOf(messageTypeString);
	}

	public enum MessageType {
		LEAVE, KICK, JOIN, CREATE, DISBAND, CHANGEABB, RECOVERY_OWNER, RECOVERY_ADMIN, RECOVERY_MEMBER;
	}
}
