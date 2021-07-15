package com.triggerise.store.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trasnaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long trans_id;
    @Column(name="player_Id")
    private long player_Id;
    @Column(name="game_Id")
    private long game_Id;
    @Column(name="trans_desc")
    private String trans_desc;
    @Column(name="game_type")
    private int amount;
    @Column(name="amount_def")
    private int amount_def;
    @CreationTimestamp
    private Date sysDate;

    public long getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(long trans_id) {
        this.trans_id = trans_id;
    }

    public long getPlayer_Id() {
        return player_Id;
    }

    public void setPlayer_Id(long player_Id) {
        this.player_Id = player_Id;
    }

    public long getGame_Id() {
        return game_Id;
    }

    public void setGame_Id(long game_Id) {
        this.game_Id = game_Id;
    }

    public String getTrans_desc() {
        return trans_desc;
    }

    public void setTrans_desc(String trans_desc) {
        this.trans_desc = trans_desc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount_def() {
        return amount_def;
    }

    public void setAmount_def(int amount_def) {
        this.amount_def = amount_def;
    }

    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }
}
