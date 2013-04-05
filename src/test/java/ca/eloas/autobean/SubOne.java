package ca.eloas.autobean;

import java.util.List;

/**
 * @author JP
 */
public interface SubOne extends Top {

    public int getOne();
    void setOne(int one);

    void setChild(Top t);
    Top getChild();

    List<Top> getList();
}
