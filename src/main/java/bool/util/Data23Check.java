package bool.util;

import bool.util.bean.Data23CheckBean;

import java.util.ArrayList;
import java.util.List;

public class Data23Check {


    public static boolean CheckMessage(byte[] buffer)
    {
        Data23CheckBean  checkBean=new Data23CheckBean();
        checkBean.setIndex(28);
        int [] dest=DataUtil.toIntArray(buffer);

//        boolean sflag = true;
//        boolean sflag1 = true;
//        boolean sflag2 = true;
//        boolean sflag3 = true;
//        boolean sflag4 = true;
//        boolean sflag5 = true;
//        boolean sflag6 = true;
//        boolean sflag7 = true;
//        boolean sflag8 = true;
//        boolean sflag9 = true;
        //bool sflag10 = true;
        while (checkBean.getIndex() < dest.length - 1)
        {
            int JK = dest[checkBean.getIndex()];
            if (JK != 1 && JK != 2 && JK != 3 && JK != 4 && JK != 5 && JK != 6 && JK != 7 && JK != 8 && JK != 9 && JK != 128)
            {
                return false;
            }
            else
            {
                switch (dest[checkBean.getIndex()])
                {
                    case 1: //:整车数据
                        checkBean.setFlag(checkvehdata(dest, checkBean));
                        break;
                    case 2: //:驱动电机数据
                        checkBean.setFlag(checkQD(dest, checkBean));
                        break;
                    case 3: //:燃料电池数据
                        checkBean.setFlag(checkRL(dest, checkBean));
                        break;
                    case 4: //:发动机数据
                        checkBean.setFlag(checkFD(dest, checkBean));
                        break;
                    case 5: //:车辆位置
                        checkBean.setFlag(checkVehLocation(dest,  checkBean));
                        break;
                    case 6: //:极值数据
                        checkBean.setFlag(checkJZ(dest,  checkBean));
                        break;
                    case 7: //:报警
                        checkBean.setFlag(checkBJ(dest,  checkBean));
                        break;
                    case 8: //:电压
                        checkBean.setFlag(checkDY(dest,  checkBean));
                        break;
                    case 9://温度
                        checkBean.setFlag(checkWD(dest,  checkBean));
                        break;
                    case -128://自定义
                        checkBean.setFlag(checkZDy(dest,  checkBean));
                        break;
                    default:
                        checkBean.setIndex(dest.length);
                        return  false;
//                        break;
                }
                if(checkBean.isFlag()){


                }else{

                    return  checkBean.isFlag();
                }
            }
        }
        return  checkBean.isFlag();
    }

    /// <summary>
    /// 校验整车数据
    /// </summary>
    /// <returns></returns>
    public static boolean checkvehdata(int[] dest,  Data23CheckBean checkBean)
    {
        try
        {
            int k=checkBean.getIndex();
            int h = k + 22;
            int F = k + 21;
            if (F > dest.length)
            {
                k = dest.length;
                checkBean.setIndex(k);
                return false;
//                return  k;
            }
            else
            {
                int PK = dest[k + 21];
                if (dest[k] == 1 && h == dest.length)
                {
                    k = k + 21;
                    checkBean.setIndex(k);
                    return true;
//                    return  k;
                }
                else if (dest[k] == 1 && (PK == 2 || PK == 3 || PK == 4 || PK == 5 || PK == 6 || PK == 7 || PK == 8 || PK == 9 || PK == 128))
                {
                    k = k + 21;
                    checkBean.setIndex(k);
                    return true;
//                    return  k;
                }
                else
                {
                    k = k + 21;
                    checkBean.setIndex(k);
                    return false;
//                    return  k;
                }
            }
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    /// <summary>
    /// 校验驱动电机数据
    /// </summary>
    /// <returns></returns>
    public static boolean checkQD(int[] dest,  Data23CheckBean checkBean)
    {
        try
        {
            int k=checkBean.getIndex();
            int dh = 12 * dest[k + 1];
            int F = dh + k + 2;
            if (F > dest.length)
            {
                k = dest.length;
                checkBean.setIndex(k);
                return false;
//                return  k;
            }
            else
            {
                int PK = dest[dh + k + 2];
                int J = k + dh + 3;
                if (dest[k] == 2 && J == dest.length)
                {
                    k = k + dh + 2;
                    checkBean.setIndex(k);
                    return true;
//                    return  k;
                }
                else if (dest[k] == 2 && (PK == 1 || PK == 3 || PK == 4 || PK == 5 || PK == 6 || PK == 7 || PK == 8 || PK == 9 || PK == 128))
                {
                    k = k + dh + 2;
                    checkBean.setIndex(k);
                    return true;
//                    return  k;
                }
                else
                {
                    k = k + dh + 2;
                    checkBean.setIndex(k);
                    return false;
//                    return  k;
                }
            }
        }
        catch   (Exception ex)
        {

            return false;
        }

    }

    /// <summary>
    /// 校验燃料电池数据
    /// </summary>
    /// <returns></returns>
    public static boolean checkRL(int[] dest,  Data23CheckBean checkBean)
    {
        try
        {
            int k=checkBean.getIndex();
            int T = dest[k + 7] * 256 + dest[k + 8];

            int F  = k + 18 + T;

            if (F > dest.length)
            {
                k = dest.length;
                checkBean.setIndex(k);
                return false;
//                return  k;
            }
            else
            {
                int PK = dest[k + 18 + T];
                int J = k + 19 + T;
                if (dest[k] == 3 && J == dest.length)
                {
                    k = k + 19 + T;
                    checkBean.setIndex(k);
                    return true;
//                    return  k;
                }
                else if (dest[k] == 3 && (PK == 1 || PK == 2 || PK == 4 || PK == 5 || PK == 6 || PK == 7 || PK == 8 || PK == 9 || PK == 128))
                {
                    k = k + 19 + T;
                    checkBean.setIndex(k);
                    return true;
//                    return  k;
                }
                else
                {
                    k = k + 19 + T;
                    checkBean.setIndex(k);
                    return false;
//                    return  k;
                }
            }

        }
        catch   (Exception ex)
        {
            return false;
        }
    }

    /// <summary>
    /// 校验发动机数据
    /// </summary>
    /// <param name="dest"></param>
    /// <param name="k"></param>
    /// <returns></returns>
    public static boolean checkFD(int[] dest,  Data23CheckBean checkBean)
    {
        try
        {
            int k=checkBean.getIndex();
            int J = k + 7;
            int F = k + 6;
            if (F > dest.length)
            {
                k = dest.length;
                checkBean.setIndex(k);
                return false;
//                return  k;
            }
            else
            {
                int PK = dest[k + 6];
                if (dest[k] == 4 && J == dest.length)
                {
                    k = k + 6;
                    checkBean.setIndex(k);
                    return true;
//                    return  k;
                }
                else if (dest[k] == 4 && (PK == 1 || PK == 2 || PK == 3 || PK == 5 || PK == 6 || PK == 7 || PK == 8 || PK == 9 || PK == 128))
                {
                    k = k + 6;
                    checkBean.setIndex(k);
                    return true;
//                    return  k;
                }
                else
                {
                    k = k + 6;
                    checkBean.setIndex(k);
                    return false;
//                    return  k;
                }
            }
        }
        catch  (Exception ex)
        {
            return false;
        }

    }

    /// <summary>
    /// 校验车辆位置数据
    /// </summary>
    /// <param name="dest"></param>
    /// <param name="k"></param>
    /// <returns></returns>
    public static boolean checkVehLocation(int[] dest,  Data23CheckBean checkBean)
    {
        try
        {
            int k=checkBean.getIndex();
            int J = k + 11;
            int F = k + 10;
            if (F > dest.length)
            {
                k = dest.length;
                checkBean.setIndex(k);
                return false;
            }
            else
            {
                int PK = dest[k + 10];
                if (dest[k] == 5 && J == dest.length)
                {
                    k = k + 10;
                    checkBean.setIndex(k);
                    return true;
                }
                else if (dest[k] == 5 && (PK == 1 || PK == 2 || PK == 3 || PK == 4 || PK == 5 || PK == 6 || PK == 7 || PK == 8 || PK == 9 || PK == 128))
                {
                    k = k + 10;
                    checkBean.setIndex(k);
                    return true;
                }
                else
                {
                    k = k + 10;
                    checkBean.setIndex(k);
                    return false;
                }
            }
        }
        catch   (Exception ex)
        {
            return false;
        }
    }


    /// <summary>
    /// 校验极值数据
    /// </summary>
    /// <param name="dest"></param>
    /// <param name="k"></param>
    /// <returns></returns>
    public static boolean checkJZ(int[] dest,  Data23CheckBean checkBean)
    {
        try
        {
            int k=checkBean.getIndex();
            int J = k + 16;

            int F = k + 15;
            if (F > dest.length)
            {
                k = dest.length;
                checkBean.setIndex(k);
                return false;
            }
            else
            {
                int PK = dest[k + 15];
                if (dest[k] == 6 && J == dest.length)
                {
                    k = k + 15;
                    checkBean.setIndex(k);
                    return true;
                }
                else if (dest[k] == 6 && (PK == 1 || PK == 2 || PK == 3 || PK == 4 || PK == 5 || PK == 7 || PK == 8 || PK == 9||PK==128))
                {
                    k = k + 15;
                    checkBean.setIndex(k);
                    return true;
                }
                else
                {
                    k = k + 15;
                    checkBean.setIndex(k);
                    return false;
                }
            }
        }
        catch  (Exception ex)
        {
            return false;
        }
    }


    /// <summary>
    /// 校验报警数据
    /// </summary>
    /// <param name="dest"></param>
    /// <param name="k"></param>
    /// <returns></returns>
    public static boolean checkBJ(int[] dest,  Data23CheckBean checkBean)
    {
        try
        {
            int k=checkBean.getIndex();
            int CountN1 = dest[k + 6];
            int CountN2 = dest[k + CountN1 + 7];
            int CountN3 = dest[k + CountN1 + CountN2 + 8];
            int CountN4 = dest[k + CountN1 + CountN2 + CountN3 + 9];
            int J = k + 4 * (CountN1 + CountN2 + CountN3 + CountN4) + 11;
            if (J > dest.length)
            {
                k = dest.length;
                checkBean.setIndex(k);
                return false;
            }
            else
            {
                int PK = dest[k + 4 * (CountN1 + CountN2 + CountN3 + CountN4) + 10];
                if (dest[k] == 7 && J == dest.length)
                {
                    k = k + CountN4 + 10;
                    checkBean.setIndex(k);
                    return true;
                }
                else if (dest[k] == 7 && (PK == 1 || PK == 2 || PK == 3 || PK == 4 || PK == 5 || PK == 6 || PK == 8 || PK == 9||PK==128))
                {
                    k = k + 4 * (CountN1 + CountN2 + CountN3 + CountN4) + 10;
                    checkBean.setIndex(k);
                    return true;
                }
                else
                {
                    k = k + 4 * (CountN1 + CountN2 + CountN3 + CountN4) + 10;
                    checkBean.setIndex(k);
                    return false;
                }
            }
        }
        catch   (Exception ex)
        {
            return false;
        }
    }

    /// <summary>
    /// 校验电压模块数据
    /// </summary>
    /// 2017-6-6 王金霞
    /// <param name="dest"></param>
    /// <param name="k"></param>
    /// <returns></returns>

    public static boolean checkDY(int[] dest,  Data23CheckBean checkBean)
    {
        List<Integer> lst = new ArrayList<Integer>();
        try
        {
            int k=checkBean.getIndex();
            int p = 0;
            int Q = 0;
            int j = k + 11; //第一个子系统总数索引
            int Nsystems = dest[k + 1];  //:子系统总数
            int Nsystem = dest[k + 2];//:第一个子系统号
            int allvoltageCount = dest[k + 7] * 256 + dest[k + 8];  //:单体电池总数
            int voltageCount = dest[j];  //:本帧单体电池总数(子系统个数=1时) 24
            lst.add(voltageCount); //第一个总数
            int F = k + 12 + 2 * voltageCount;
            if (F > dest.length)
            {
                k = dest.length;
                checkBean.setIndex(k);
                return false;
            }
            else
            {
                int PK = dest[k + 12 + 2 * voltageCount];
                int J = k + 13 + 2 * voltageCount;
                if (Nsystems == 1)
                {
                    if (dest[k] == 8 && J == dest.length)   //dest[2 * voltageCount + j + 1] == 9
                    {
                        k = k + 12 + 2 * voltageCount;
                        checkBean.setIndex(k);
                        return true;
                    }
                    else if (dest[k] == 8 && (PK == 1 || PK == 2 || PK == 3 || PK == 4 || PK == 5 || PK == 6 || PK == 7 || PK == 9 || PK == 128))
                    {
                        k = k + 12 + 2 * voltageCount;
                        checkBean.setIndex(k);
                        return true;
                    }
                    else
                    {
                        k = k + 12 + 2 * voltageCount;
                        checkBean.setIndex(k);
                        return false;
                    }
                }
                else
                {
                    for (int i = 1; i <= Nsystems - 1; i++)
                    {
                        int dx = j;
                        for (int u = 0; u < i; u++)
                        {
                            //除去第一个之外，每一个总数的索引
                            dx = (2 * lst.get(u) + dx + 10);
                            Q = dx;
                            int px = 2 * dest[dx];
                            if ((dest[Q + px + 1] == i + 2) || dest[Q + px + 1] == 9)
                            {
                                p++;
                            }
                        }
                        lst.add(dest[dx]);
                    }

                    if (p == Nsystems - 1 && (dest[2 * voltageCount + j + 1] == 2))
                    {
                        k = 2 * dest[Q] + Q + 1;
                        checkBean.setIndex(k);
                        return true;
                    }
                    else
                    {
                        k = 2 * dest[Q] + Q + 1;
                        checkBean.setIndex(k);
                        return false;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    /// <summary>
    /// 校验温度模块数据
    /// 2017-6-6 王金霞
    /// </summary>

    public static boolean checkWD(int[] dest,  Data23CheckBean checkBean)
    {
        List<Integer> tlst = new ArrayList<Integer>();
        try
        {
            int k=checkBean.getIndex();
            int p = 0;
            int Nsystems = dest[k + 1];  //:可充电储能子系统个数
            int Nsystem = dest[k + 2];//:第一个可充电储能子系统号
            int tempCount = dest[k + 3] * 256 + dest[k + 4];  //:可充电储能温度探针个数
            int j = k + 4; //可充电储能温度探针个数索引结束位置
            tlst.add(tempCount); //第一个可充电储能温度探针个数


            int F = k + Nsystems * (tempCount + 3) + 2;
            if (F > dest.length)
            {
                k = dest.length;
                checkBean.setIndex(k);
                return false;
            }
            else
            {
                int PK = dest[k + Nsystems * (tempCount + 3) + 2];
                int J = k + Nsystems * (tempCount + 3) + 3;
                if (Nsystems == 1)
                {
                    if (dest[k] == 9 && J == dest.length)
                    {
                        k = k + Nsystems * (tempCount + 3) + 2;
                        checkBean.setIndex(k);
                        return true;
                    }
                    else if (dest[k] == 9 && (PK == 1 || PK == 2 || PK == 3 || PK == 4 || PK == 5 || PK == 6 || PK == 7 || PK == 8 || PK == 128))
                    {
                        k = k + Nsystems * (tempCount + 3) + 2;
                        checkBean.setIndex(k);
                        return true;
                    }
                    else
                    {
                        k = k + Nsystems * (tempCount + 3) + 2;
                        checkBean.setIndex(k);
                        return false;
                    }
                }
                else
                {
                    for (int i = 1; i <= Nsystems - 1; i++)
                    {
                        int dx = j;   //可充电储能温度探针个数索引结束位置
                        for (int u = 0; u < i; u++)
                        {
                            dx = (tlst.get(u) + dx + 3); //除去第一个之外，每一个可充电储能温度探针个数索引的结束位置
                            int px = dest[dx - 1] * 256 + dest[dx];  //:除去第一个之外，每一个可充电储能温度探针个数
                            //if (dx + px + 1 > dest.Count())
                            //{
                            //    Console.WriteLine("yguyguguhuihuby");
                            //    if ((dx + px + 1 + j + 2) == dest.Count())
                            //    {
                            //        p++;
                            //    }
                            //}
                            //else
                            //{
                            if (dest[dx + px + 1] == i + 2)
                            {
                                p++;
                            }
                            //}
                        }
                        tlst.add(dest[dx - 1] * 256 + dest[dx]);
                    }

                    if (p == Nsystems - 2)
                    {
                        k = k + Nsystems * (tempCount + 3) + 2;
                        checkBean.setIndex(k);
                        return true;
                    }
                    else
                    {
                        k = k + Nsystems * (tempCount + 3) + 2;
                        checkBean.setIndex(k);
                        return false;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            return false;
        }
    }


    public static boolean checkZDy(int[] dest,  Data23CheckBean checkBean)
    {
        int k=checkBean.getIndex();
        int length = dest[k+1] * 256 + dest[k + 2];
        k = k + length + 3;
        checkBean.setIndex(k);
        return true;
    }






}
