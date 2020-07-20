package com.tosok.user.IamPortRequest;

import java.sql.Date;

public class CancelData {

	private int pay_seq; // 결제 번호
	private String imp_uid; // 아임포트 결제 고유번호
	private String merchant_uid; // 아임포트 결제 고유번호
	private String amount; // 취소 금액
	private String reason; // 취소 사유
	private String refund_holder; // 무통장 입금 존재시만 필요
	private String refund_bank; // 무통장 입금 존재시만 필요
	private String refund_account; // 무통장 입금 존재시만 필요

	private String status;
	private String receipt_url;
	private Date cancel_date;

	public int getPay_seq() {
		return pay_seq;
	}

	public void setPay_seq(int pay_seq) {
		this.pay_seq = pay_seq;
	}

	public String getImp_uid() {
		return imp_uid;
	}

	public void setImp_uid(String imp_uid) {
		this.imp_uid = imp_uid;
	}

	public String getMerchant_uid() {
		return merchant_uid;
	}

	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRefund_holder() {
		return refund_holder;
	}

	public void setRefund_holder(String refund_holder) {
		this.refund_holder = refund_holder;
	}

	public String getRefund_bank() {
		return refund_bank;
	}

	public void setRefund_bank(String refund_bank) {
		this.refund_bank = refund_bank;
	}

	public String getRefund_account() {
		return refund_account;
	}

	public void setRefund_account(String refund_account) {
		this.refund_account = refund_account;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCancel_date() {
		return cancel_date;
	}

	public void setCancel_date(Date cancel_date) {
		this.cancel_date = cancel_date;
	}

	public String getReceipt_url() {
		return receipt_url;
	}

	public void setReceipt_url(String receipt_url) {
		this.receipt_url = receipt_url;
	}

}