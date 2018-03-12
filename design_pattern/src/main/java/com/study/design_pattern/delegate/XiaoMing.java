package com.study.design_pattern.delegate;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class XiaoMing implements Mishu {

    private Mishu mishu;

    public XiaoMing() {
        this.mishu = new TheBoss();
    }

    public XiaoMing(Mishu mishu) {
        this.mishu = mishu;
    }

    @Override
    public void kaihui() {
        System.out.println("小明在代替老板开会");
        mishu.kaihui();
    }

    public static void main(String[] args) {
        TheCEO ceo = new TheCEO();
        XiaoMing xiaoMing = new XiaoMing(ceo);

        xiaoMing.kaihui();

        xiaoMing = new XiaoMing();
        xiaoMing.kaihui();
    }
}
