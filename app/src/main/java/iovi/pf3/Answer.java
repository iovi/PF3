package iovi.pf3;

/**
 * Created by Aspenson on 16.01.2016.
 */
public class Answer {
    public int p;
    public int f;
    public Answer(int p, int f){
        this.p=p;
        this.f=f;
    }
    public boolean equalTo(Answer a){
        if (this.f==a.f && this.p==a.p)
            return true;
        return false;
    }
}
