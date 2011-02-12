
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author viswanathgs
 */
public class MachiRecord {
    RecordStore recordStore = null;
    String recordName;

    public MachiRecord (String tname) {
        recordName = tname;
    }

    byte[] MachiToBytes (Machi machi) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(bytestream);

        writer.writeUTF(machi.name);
        writer.writeUTF(machi.type);
        writer.writeInt(machi.balance);
        writer.writeInt(machi.color);
        writer.writeInt(machi.history.historySize);
        for (int i = 0; i < machi.history.historySize; i++) {
            writer.writeInt(machi.history.transHistory[i].amount);
            writer.writeLong(machi.history.transHistory[i].timestamp);
            writer.writeUTF(machi.history.transHistory[i].comment);
        }

        writer.flush();

        byte[] rec = bytestream.toByteArray();

        writer.close();
        bytestream.close();

        return rec;
    }

    Machi BytesToMachi (byte[] rec) throws IOException {
        Machi machi = new Machi();
        ByteArrayInputStream bytestream = new ByteArrayInputStream(rec);
        DataInputStream reader = new DataInputStream(bytestream);

        machi.name = reader.readUTF();
        machi.type = reader.readUTF();
        machi.balance = reader.readInt();
        machi.color = reader.readInt();
        machi.history.historySize = reader.readInt();
        for (int i = 0; i < machi.history.historySize; i++) {
            machi.history.transHistory[i] = new Transaction();
            machi.history.transHistory[i].amount = reader.readInt();
            machi.history.transHistory[i].timestamp = reader.readLong();
            machi.history.transHistory[i].comment = reader.readUTF();
        }

        reader.close();
        bytestream.close();

        return machi;
    }

    void addRecord (String mname, int mbalance) throws RecordStoreException, IOException {
        if (recordExists(mname))
            return;

        Machi machi = new Machi(mname, mbalance);

        byte[] rec = MachiToBytes(machi);
        recordStore.addRecord(rec, 0, rec.length);
    }

    void deleteRecord (String mname) throws RecordStoreException, IOException {
        RecordEnumeration recordEnum = recordStore.enumerateRecords(null, null, true);
        
        while (recordEnum.hasNextElement()) {
            int recordId = recordEnum.nextRecordId();
            byte[] rec = recordStore.getRecord(recordId);

            Machi machi = BytesToMachi(rec);
            if (mname.equals(machi.name)) {
                recordStore.deleteRecord(recordId);
                break;
            }
        }
    }

    void updateRecord (String mname, int delta, String comment) throws RecordStoreException, IOException {

        RecordEnumeration recordEnum = recordStore.enumerateRecords(null, null, true);

        while (recordEnum.hasNextElement()) {
            int recordId = recordEnum.nextRecordId();
            byte[] rec = recordStore.getRecord(recordId);

            Machi machi = BytesToMachi(rec);
            if (mname.equals(machi.name)) {
                machi.balance += delta;
                machi.history.addToHistory(new Transaction(delta, comment));

                rec = MachiToBytes(machi);
                recordStore.setRecord(recordId, rec, 0, rec.length);
                break;
            }
        }
    }

    boolean recordExists(String mname) throws RecordStoreException, IOException {
        boolean flag = false;

        RecordEnumeration recordEnum = recordStore.enumerateRecords(null, null, true);

        while (recordEnum.hasNextElement()) {
            int recordId = recordEnum.nextRecordId();
            byte[] rec = recordStore.getRecord(recordId);

            Machi machi = BytesToMachi(rec);
            if (mname.equals(machi.name)) {
                flag = true;
                break;
            }
        }

        return flag;
    }
}
