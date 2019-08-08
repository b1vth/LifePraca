package me.b1vth420.LifePraca.Data;

import org.bukkit.configuration.file.FileConfiguration;

public class Lang {
    private static Lang inst;
    private FileConfiguration cfg = FileManager.getLang();

    private Lang(){
        inst = this;
    }

    public String sucessfulCreatePatternMessage;
    public String sucessfulCreateBuildingMessage;
    public String moneyAddMessage;
    public String brokenLegMessage;
    public String promotionMessage;
    public String buildingCheckStartedMessage;
    public String buildingTeleportMessage;
    public String accountBalanceMessage;
    public String forcedJobChangeMessage;
    public String dutyJoinMessage;
    public String dutyLeaveMessage;
    public String bossBarOnDeath;
    public String jobJoinMessage;
    public String brokenLegHelpMessageTo;
    public String brokenLegHelpMessageWho;
    public String playerHelpedMessage;
    public String playerFrezedMessage;
    public String playerUnFrezedMessage;
    public String finePolicemanMessage;
    public String finePlayerMessage;
    public String mutePolicemanMessage;
    public String mutePlayerMessage;
    public String jobLevelUpMessage;
    public String buildingCheckInProgressMessage;
    public String premiumJobRewardMessage;
    public String benefitMessage;
    public String buildingSuccessfulBuildMessage;
    public String buildingPartlySuccessfulMessage;
    public String buildingNotSuccessfulMessage;
    public String buildingSuccessfulMoneyMessage;
    public String buildingNotSuccessfulMoneyMessage;

    public void load(){
        sucessfulCreatePatternMessage = cfg.getString("sucessfulCreatePatternMessage");
        sucessfulCreateBuildingMessage = cfg.getString("sucessfulCreateBuildingMessage");
        moneyAddMessage = cfg.getString("moneyAddMessage");
        brokenLegMessage = cfg.getString("brokenLegMessage");
        promotionMessage = cfg.getString("promotionMessage");
        buildingCheckStartedMessage = cfg.getString("buildingCheckStartedMessage");
        buildingTeleportMessage = cfg.getString("buildingTeleportMessage");
        accountBalanceMessage = cfg.getString("accountBalanceMessage");
        forcedJobChangeMessage = cfg.getString("forcedJobChangeMessage");
        dutyJoinMessage = cfg.getString("dutyJoinMessage");
        dutyLeaveMessage = cfg.getString("dutyLeaveMessage");
        bossBarOnDeath = cfg.getString("bossBarOnDeath");
        jobJoinMessage = cfg.getString("jobJoinMessage");
        brokenLegHelpMessageTo = cfg.getString("brokenLegHelpMessageTo");
        brokenLegHelpMessageWho = cfg.getString("brokenLegHelpMessageWho");
        playerHelpedMessage = cfg.getString("playerHelpedMessage");
        playerFrezedMessage = cfg.getString("playerFrezedMessage");
        playerUnFrezedMessage = cfg.getString("playerUnFrezedMessage");
        finePolicemanMessage = cfg.getString("finePolicemanMessage");
        finePlayerMessage = cfg.getString("finePlayerMessage");
        mutePolicemanMessage = cfg.getString("mutePolicemanMessage");
        mutePlayerMessage = cfg.getString("mutePlayerMessage");
        jobLevelUpMessage = cfg.getString("jobLevelUpMessage");
        buildingCheckInProgressMessage = cfg.getString("buildingCheckInProgressMessage");
        premiumJobRewardMessage = cfg.getString("premiumJobRewardMessage");
        benefitMessage = cfg.getString("benefitMessage");
        buildingSuccessfulBuildMessage = cfg.getString("buildingSuccessfulBuildMessage");
        buildingPartlySuccessfulMessage = cfg.getString("buildingPartlySuccessfulMessage");
        buildingNotSuccessfulMessage = cfg.getString("buildingNotSuccessfulMessage");
        buildingSuccessfulMoneyMessage = cfg.getString("buildingSuccessfulMoneyMessage");
        buildingNotSuccessfulMoneyMessage = cfg.getString("buildingNotSuccessfulMoneyMessage");
    }

    public static Lang getInst(){
        if (inst == null) {
            return new Lang();
        }
        return inst;
    }
}
